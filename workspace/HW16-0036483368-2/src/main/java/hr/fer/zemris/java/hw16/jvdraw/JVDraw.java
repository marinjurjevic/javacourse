package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.color.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.model.SimpleDrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Circle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.shapes.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.shapes.Line;
import hr.fer.zemris.java.hw16.jvdraw.shapes.ObjectCreator;

/**
 * Application JVDraw represents a mini-paint like application. It offers drawing variuos graphical objects
 * such as lines and circles.
 * @author Marin Jurjevic
 *
 */
public class JVDraw extends JFrame {
	
	/**
	 * serial UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * foreground color provider
	 */
	private JColorArea fg = new JColorArea(Color.RED);
	
	/**
	 * background color provider
	 */
	private JColorArea bg = new JColorArea(Color.BLUE);
	
	/**
	 * Drawing model represents model for geometrical objects
	 */
	private DrawingModel model = new SimpleDrawingModel();
	
	/**
	 * reference to drawing area
	 */
	private JDrawingCanvas canvas;
	
	/**
	 * DrawingObjectListModel is keeps all active objects listed.
	 */
	private DrawingObjectListModel listModel;
	
	/**
	 * Swing component for rendering our list model
	 */
	private JList<GeometricalObject> objectList;
	
	/**
	 * currentPath where file will be saved by default, null if none selected yet
	 */
	private Path currentPath;
	
	/**
	 * if file has been saved and not modified since
	 */
	private boolean saved;
	
	/**
	 * Creates new JVDraw and prepares to start application.
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(500, 250);
		setTitle("JVDraw");
		setSize(800,800);
		
		initGUI();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(!saved){
					int result= JOptionPane.showConfirmDialog(JVDraw.this,
							"File has been changed since last save. Do you want to save it before exit?",
							"Save file", JOptionPane.YES_NO_CANCEL_OPTION);
					
					if (result == JOptionPane.CANCEL_OPTION) {
						return;
					} else if (result == JOptionPane.YES_OPTION) {
							saveFile();
					}
					
				}
				dispose();
			}

		});
	}
	
	/**
	 * Initialises graphical user interface.
	 */
	private void initGUI(){
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		createToolbar();
		createLabel();
		createCanvas();
		
		createList();
		createMenu();
		
		//listener for changes
		model.addDrawingModelListener(new DrawingModelListener() {
			
			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				saved=false;
			}
			
			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				saved=false;
			}
			
			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				saved=false;
			}
		});
	}
	
	/**
	 * Creates toolbar and adds colors and shapes to be drawn.
	 */
	private void createToolbar(){
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(false);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(fg);
		toolBar.add(bg);
		
		ButtonGroup shapes = new ButtonGroup();
		JToggleButton lineButton = new JToggleButton("Line");
		lineButton.addActionListener(l->{
			canvas.setObjectCreator(new ObjectCreator() {
				@Override
				public GeometricalObject create(Point startPoint, Point endPoint, Color fgColor, Color bgColor) {
					return new Line(startPoint,endPoint,fgColor);
				}
			});
		});
		shapes.add(lineButton);
		
		JToggleButton circleButton = new JToggleButton("Circle");
		circleButton.addActionListener(l->{
			canvas.setObjectCreator(new ObjectCreator() {
				@Override
				public GeometricalObject create(Point startPoint, Point endPoint, Color fgColor, Color bgColor) {
					return new Circle(startPoint,endPoint,fgColor);
				}
			});
		});
		shapes.add(circleButton);
		
		JToggleButton filledCircleButton = new JToggleButton("Filled circle");
		filledCircleButton.addActionListener(l->{
			canvas.setObjectCreator(new ObjectCreator() {
				@Override
				public GeometricalObject create(Point startPoint, Point endPoint, Color fgColor, Color bgColor) {
					return new FilledCircle(startPoint,endPoint,fgColor, bgColor);
				}
			});
		});
		shapes.add(filledCircleButton);
		
		toolBar.add(lineButton);
		toolBar.add(circleButton);
		toolBar.add(filledCircleButton);
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	/**
	 * Creates label for tracking information about current foreground and background colors.
	 */
	private void createLabel(){
		String initialText = "Foreground color: " + getRGBString(fg.getCurrentColor())+", Background color: " + getRGBString(bg.getCurrentColor());
		JLabel label = new JLabel(initialText);
		
		fg.addColorChangeListener(new ColorChangeListener() {
			
			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				label.setText("Foreground color: " + getRGBString(newColor)+", "+label.getText().split(", ")[1]);
			}
		});
		
		bg.addColorChangeListener(new ColorChangeListener() {
			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				label.setText(label.getText().split(", ")[0] + ", Background color: " + getRGBString(newColor));
			}
		});
		
		this.getContentPane().add(label, BorderLayout.PAGE_END);
	}
	
	/**
	 * Returns string representation of a color as (R,G,B).
	 * @param color color to be converted to number components 
	 * @return String representation of color object
	 */
	private String getRGBString(Color color){
		return "("+color.getRed()+","+color.getGreen()+","+color.getBlue()+")";
	}
	
	/**
	 * Creates canvas for drawing various shapes.
	 */
	private void createCanvas(){
		
		canvas = new JDrawingCanvas(fg, bg, model);
		
		getContentPane().add(canvas, BorderLayout.CENTER);
	}
	
	/**
	 * Creates list model and JList swing component for tracking current objects in workspace.
	 */
	private void createList(){
		listModel = new DrawingObjectListModel(model);
		model.addDrawingModelListener(listModel);
		objectList = new JList<GeometricalObject>(listModel);

		objectList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = objectList.getSelectedIndex();
					model.update(index);
					
				}
			}
		});
		objectList.getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "deleteObject");
		objectList.getActionMap().put("deleteObject", new AbstractAction("deleteObject") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = objectList.getSelectedIndex();
				if(index != -1){
					model.remove(index);
				}
			}
		});

		
		JScrollPane scrollPane = new JScrollPane(objectList);
		scrollPane.setPreferredSize(new Dimension(180, 500));
		getContentPane().add(scrollPane, BorderLayout.EAST);
	}
	
	/**
	 * Creates menu bar and file menu with some actions.
	 */
	private void createMenu(){
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("JVDraw (*.jvd)", "jvd"));
				fc.setDialogTitle("Open sketch");
				if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}

				File fileName = fc.getSelectedFile();
				Path filePath = fileName.toPath();
				
				List<String> shapes = null;
				try{
					shapes = Files.readAllLines(filePath);
				}catch(IOException ioe){}
				
				for(String s : shapes){
					String[] comps = s.split(" ");			//   LINE        ----     CIRCLE
					int c1 = Integer.parseInt(comps[1]);	// start point x     |   center x
					int c2 = Integer.parseInt(comps[2]);	// start point y 	 |	 center y
					int c3 = Integer.parseInt(comps[3]);	// end point x		 |   radius
					int c4 = Integer.parseInt(comps[4]);	// end point y 		 |   RED
					int c5 = Integer.parseInt(comps[5]);	// RED				 |   GREEN	
					int c6 = Integer.parseInt(comps[6]);	// GREEN			 |   BLUE
					
					if(comps[0].equals("LINE")){
						int c7 = Integer.parseInt(comps[7]);	//BLUE for line
						model.add(new Line(new Point(c1,c2), new Point(c3,c4), new Color(c5, c6, c7)) );
					}else if(comps[0].equals("CIRCLE")){
						model.add(new Circle(new Point(c1,c2), c3, new Color(c4, c5, c6)));
					}else{
						// fill color for filledCircle
						int c7 = Integer.parseInt(comps[7]);
						int c8 = Integer.parseInt(comps[8]);
						int c9 = Integer.parseInt(comps[9]);
						model.add(new FilledCircle(new Point(c1,c2), c3, new Color(c4, c5, c6), new Color(c7, c8, c9)));
					}
				}
			}
		});
		fileMenu.add(open);
		
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentPath == null){
					saveFileAs();
				}else{
					saveFile();
				}
			}
		});
		fileMenu.add(save);
		
		JMenuItem saveAs = new JMenuItem("Save as...");
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveFileAs();
			}
		});
		fileMenu.add(saveAs);
		
		JMenuItem export = new JMenuItem("Export");
		export.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int minX = canvas.getWidth();
				int minY = canvas.getHeight();
				int maxX = 0;
				int maxY = 0;
				int size = model.getSize();
				for(int i = 0; i<size; i++){
					int x = model.getObject(i).getBoundingBox().x;
					int y = model.getObject(i).getBoundingBox().y;
					int w = model.getObject(i).getBoundingBox().width;
					int h = model.getObject(i).getBoundingBox().height;
					System.out.println(model.getObject(i).toString()+" "+model.getObject(i).getBoundingBox());
					if(x< minX)minX=x;
					if(y< minY)minY=y;
					if(x+w>maxX)maxX = x+w;
					if(y+h>maxY)maxY = y+h;
				}
				int width = Math.abs(maxX-minX);
				int height = Math.abs(maxY-minY);
				System.out.println(new Rectangle(minX,minY,width,height));
				
				JFileChooser fc = new JFileChooser("./");

				FileNameExtensionFilter png = new FileNameExtensionFilter("PNG (*.png)", ".png");
				FileNameExtensionFilter gif = new FileNameExtensionFilter("GIF (*.gif)", ".gif");
				FileNameExtensionFilter jpg = new FileNameExtensionFilter("JPG (*.jpg)", ".jpg");
				
				fc.addChoosableFileFilter(png);
				fc.addChoosableFileFilter(gif);
				fc.addChoosableFileFilter(jpg);
				fc.setFileFilter(png);
				int status = fc.showSaveDialog(null);

				if (status != JFileChooser.OPEN_DIALOG) { return; }
				File f = fc.getSelectedFile();
				
				BufferedImage image = null;
				try {
					image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
					Graphics2D g = image.createGraphics();
					g.setColor(Color.white);
					g.fillRect(0, 0, width, height);

					for (int i = 0; i < model.getSize(); i++) {
						model.getObject(i).draw(g, new Rectangle(minX,minY,width,height));
					}
					g.dispose();
				} catch (Exception e2) {
					return;
				}
				try {
					if (fc.getFileFilter().equals(png)) {
						f = setExtension(f, ".png");
						ImageIO.write(image, "png", f);
					} else if (fc.getFileFilter().equals(gif)) {
						f = setExtension(f, ".gif");
						ImageIO.write(image, "gif", f);
					} else if (fc.getFileFilter().equals(jpg)) {
						f = setExtension(f, ".jpg");
						ImageIO.write(image, "jpg", f);
					}
				} catch (Exception ex) {
				}
			}
		});
		fileMenu.addSeparator();
		fileMenu.add(export);
		
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Adds given extension to file object.
	 * @param f file object
	 * @param extension extension to be set
	 * @return file with given extension
	 */
	private static File setExtension(File f, String extension) {
		if (f.getAbsoluteFile().toString().endsWith(extension) || 
				f.getAbsoluteFile().toString().endsWith(extension.toUpperCase())) {
			return f;
		}
		return new File(f.getAbsolutePath() + extension);
	}
	
	/**
	 * Asks user path to wanted location where *.jvd file will be saved.
	 */
	private void saveFileAs(){
		JFileChooser jfc = new JFileChooser() {
			private static final long serialVersionUID = 1L;

			@Override
			public void approveSelection() {
				File f = getSelectedFile();
				if (f.exists() && getDialogType() == SAVE_DIALOG) {
					int result = JOptionPane.showConfirmDialog(JVDraw.this, "File already exists, do you want to overwrite it",
							"File exists", JOptionPane.YES_NO_CANCEL_OPTION);
					switch (result) {
					case JOptionPane.YES_OPTION:
						super.approveSelection();
						return;
					case JOptionPane.NO_OPTION:
						return;
					case JOptionPane.CLOSED_OPTION:
						return;
					case JOptionPane.CANCEL_OPTION:
						cancelSelection();
						return;
					}
				}
				super.approveSelection();
			}
		};
		jfc.setDialogTitle("Save as...");
		if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(JVDraw.this, "File has not been saved!",
					"Warning!", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		currentPath = jfc.getSelectedFile().toPath();
		saveFile();
		
	}
	
	/**
	 * Saves file to currentPath location.
	 */
	private void saveFile(){
		int size = model.getSize();
		StringBuilder sb = new StringBuilder(size*30);
		for(int i = 0; i<size;i++){
			sb.append(model.getObject(i).getInfo()).append("\n");
		}
		
		byte[] podatci = sb.toString().getBytes(StandardCharsets.UTF_8);

		try {
			Files.write(currentPath, podatci);
		} catch (IOException e1) {
		}

		JOptionPane.showMessageDialog(JVDraw.this,"File has been saved","Saved", JOptionPane.INFORMATION_MESSAGE);
		saved = true;
	}
	
	/**
	 * Application entry point.
	 * @param args none
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}

}
