package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * <tt>SmartScriptEngine</tt> is an engine for executing smart scripts.
 * @author Marin Jurjevic
 *
 */
public class SmartScriptEngine {

	/**
	 * constant string name for mapping temporary stack inside object multistack object
	 */
	private static final String TEMP_STACK = "TEMP"; 
	
	/**
	 * root node in document hierarchy
	 */
	private DocumentNode documentNode;
	
	/**
	 * instance of request context with information
	 */
	private RequestContext requestContext;
	
	/**
	 * <tt>ObjectMultistack</tt> object for executing engine operations
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Creates new <tt>SmartScriptEngine</tt> and prepares it for execution.
	 * @param documentNode top-level node in document hierarchy
	 * @param requestContext context to write output
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		super();
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	
	/**
	 * Performs engine execution based on initialized values.
	 */
	public void execute(){
		documentNode.accept(visitor);
	}
	
	/**
	 * Private INodeVisitor object for engine execuion.
	 */
	private INodeVisitor visitor = new INodeVisitor(){

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.err.println("Error occured while writing on output stream.");
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String varName = node.getVariable().getName();
			ValueWrapper start = new ValueWrapper(node.getStartExpression().asText());
			ValueWrapper end = new ValueWrapper(node.getEndExpression().asText());
			ValueWrapper step = node.getStepExpression() == null ? null : new ValueWrapper(node.getStepExpression().asText());
			multistack.push(varName, start);
			while(start.numCompare(end.getValue())<=0){
				iterateChildren(node);
				
				start = multistack.peek(varName);
				start.increment(step.getValue());
				multistack.push(varName, start);
			}
			multistack.pop(varName);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Element[] elems = node.getElements();
			
			for(Element e : elems){
				if(e.getClass().equals(ElementConstantDouble.class)){
					multistack.push(TEMP_STACK, new ValueWrapper(((ElementConstantDouble)e).getValue()));
				}else if(e.getClass().equals(ElementConstantInteger.class)){
					multistack.push(TEMP_STACK, new ValueWrapper(((ElementConstantInteger)e).getValue()));
				}else if(e.getClass().equals(ElementString.class)){
					multistack.push(TEMP_STACK, new ValueWrapper(((ElementString)e).getValue()));
				}else if(e.getClass().equals(ElementOperator.class)){	// operator
					String symbol = ((ElementOperator)e).getSymbol();
					performBinaryOperation(symbol);
				}else if(e.getClass().equals(ElementVariable.class)){	// variable
					String varName = ((ElementVariable)e).getName();
					multistack.push(TEMP_STACK, multistack.peek(varName));
				}else{ // function
					String funName = ((ElementFunction)e).getName();
					performFunction(funName);
				}
			}
			
			writeNonEmptyStack();
			
		}
		
		/**
		 * Performs given function. 
		 * @param funName name of function to be performed
		 */
		private void performFunction(String funName){
			ValueWrapper arg1;
			ValueWrapper arg2;
			
			switch(funName){
			case "sin":
				Object o = multistack.pop(TEMP_STACK).getValue();
				multistack.push(TEMP_STACK, new ValueWrapper(Math.sin(o.getClass() == Integer.class?(Integer)o:(Double)o)));
				break;
			case "decfmt":
				String format = (String) multistack.pop(TEMP_STACK).getValue();
				arg1 = multistack.pop(TEMP_STACK);
				DecimalFormat df = new DecimalFormat(format);
				multistack.push(TEMP_STACK, new ValueWrapper(df.format(arg1.getValue())));
				break;
			case "dup":
				multistack.push(TEMP_STACK, multistack.peek(TEMP_STACK));
				break;
			case "swap":
				arg1 = multistack.pop(TEMP_STACK);
				arg2 = multistack.pop(TEMP_STACK);
				multistack.push(TEMP_STACK, arg1);
				multistack.push(TEMP_STACK, arg2);
				break;
			case "setMimeType":
				String mimeType = (String)multistack.pop(TEMP_STACK).getValue();
				requestContext.setMimeType(mimeType);
				break;
			case "paramGet":
				getParameters("param");
				break;
			case "pparamGet":
				getParameters("persistent");
				break;
			case "pparamSet":
				setParameters("persistent");
				break;
			case "pparamDel":
				delParameters("persistent");
				break;
			case "tparamGet":
				getParameters("temp");
				break;
			case "tparamSet":
				setParameters("temp");
				break;
			case "tparamDel":
				delParameters("temp");
				break;
			}
		}
		
		/**
		 * Pushes specified parameter on temporary stack.
		 * @param type regular/persistent/temp 
		 */
		private void getParameters(String type){
			ValueWrapper defValue = multistack.pop(TEMP_STACK);
			String parName = (String)multistack.pop(TEMP_STACK).getValue();
			String parValue;
			
			if(type.equals("param")){
				parValue = requestContext.getParameter(parName);
			}else if(type.equals("persistent")){
				parValue = requestContext.getPersistentParameter(parName);
			}else{
				parValue = requestContext.getTemporaryParameter(parName);
			}
			
			if(parValue == null){
				multistack.push(TEMP_STACK, defValue);
			}else{
				multistack.push(TEMP_STACK, new ValueWrapper(parValue));
			}
		}
		
		/**
		 * Sets parameter in given parameter map in request context.
		 * @param type persistent/temp 
		 */
		private void setParameters(String type){
			String parName = (String)multistack.pop(TEMP_STACK).getValue();
			Object o = multistack.pop(TEMP_STACK).getValue();
			String value;
			if(o.getClass() == Integer.class){
				value = Integer.toString((Integer)o);
			}else if(o.getClass() == Double.class){
				value = Double.toString((Double)o);
			}else{
				value = (String)o;
			}
			
			if(type.equals("persistent")){
				requestContext.setPersistentParameter(parName, value);
			}else{
				requestContext.setTemporaryParameter(parName, value);
			}
		}
		
		/**
		 * Deletes parameter in given parameter map in request context.
		 * @param type persistent/temp 
		 */
		private void delParameters(String type){
			String parName = (String)multistack.pop(TEMP_STACK).getValue();
			
			if(type.equals("persistent")){
				requestContext.removePersistentParameter(parName);
			}else{
				requestContext.removeTemporaryParameter(parName);
			}
		}
		
		/**
		 * Recursive call for printing objects on stack if there are any.
		 */
		private void writeNonEmptyStack(){
			ValueWrapper arg;
			try{
				arg = multistack.pop(TEMP_STACK);
			}catch(EmptyMultistackException e){
				return;
			}
			writeNonEmptyStack();
			try{
				Object value = arg.getValue();
				if(value.getClass().equals(String.class)){
					requestContext.write((String)value);
				}else if(value.getClass().equals(Integer.class)){
					requestContext.write(Integer.toString((Integer)value));
				}else{
					requestContext.write(Double.toString((Double)value));
				}
				System.out.println(value);
			}catch(IOException ignorable){
			}
			
		}
		
		/**
		 * Performs simple binary operations (+,-,/ or *).
		 * @param symbol supported operation symbol as in description
		 */
		private void performBinaryOperation(String symbol){
			ValueWrapper arg1 = multistack.pop(TEMP_STACK);
			ValueWrapper arg2 = multistack.pop(TEMP_STACK);
			switch(symbol){
			case "+":
				arg1.increment(arg2.getValue());
				break;
			case "-":
				arg1.decrement(arg2.getValue());
				break;
			case "/":
				arg1.divide(arg2.getValue());
				break;
			case "*":
				arg1.multiply(arg2.getValue());
				break;
			default:
				throw new UnsupportedOperationException("Operation is not supported by this script");
			}
			
			multistack.push(TEMP_STACK, arg1);
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			iterateChildren(node);
		}
		
		/**
		 * Iterates all node children.
		 * @param node parent node
		 */
		private void iterateChildren(Node node){
			for(int i = 0, n=node.numberOfChildren(); i<n; i++){
				node.getChild(i).accept(this);
			}
		}
		
	};
}
