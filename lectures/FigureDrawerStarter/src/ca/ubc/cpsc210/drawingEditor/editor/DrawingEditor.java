package ca.ubc.cpsc210.drawingEditor.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import ca.ubc.cpsc210.drawingEditor.figures.AbstractFigure;
import ca.ubc.cpsc210.drawingEditor.figures.Figure;
import ca.ubc.cpsc210.drawingEditor.tools.OvalTool;
import ca.ubc.cpsc210.drawingEditor.tools.MoveTool;
import ca.ubc.cpsc210.drawingEditor.tools.RectangleTool;
import ca.ubc.cpsc210.drawingEditor.tools.SelectionTool;
import ca.ubc.cpsc210.drawingEditor.tools.Tool;

/**
 * The main window of the drawing application.
 */
@SuppressWarnings("serial")
public class DrawingEditor extends JFrame {
	private List<Drawing> drawings;
	private List<Tool> tools;
	
	private static int WIDTH = 500;
	private static int HEIGHT = 500;
	private static int TOOL_WIDTH = 100;

	// Maintain references to current tool and current drawing
	private Drawing currentDrawing;
	private Tool activeTool;
	

	// Constructor
	public DrawingEditor() {
		super("DrawingEditor");
		drawings=new ArrayList<Drawing>();
		tools=new ArrayList<Tool>();
		
		// Initialize Fields

		activeTool = null;
		currentDrawing = null;
		
		// Initialize graphics
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		createMenus();
		createTools();
		addNewDrawing();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Creates a new drawing and adds it to the editor
	 */
	private void addNewDrawing() {
		Drawing newDrawing = new Drawing(this);
		
		if (currentDrawing != null) {
			// Remove the current drawing from the graphics hierarchy
			remove(currentDrawing);
		}
		drawings.add(newDrawing);
		currentDrawing = newDrawing;
		
		add(newDrawing, BorderLayout.CENTER);
		validate();
	}
	
	/**
	 * Adds given figure to drawing
	 * @param f  the figure to add to the drawing
	 */
	public void addToDrawing(AbstractFigure f) {
		currentDrawing.addFigure(f);
	}
	
	/**
	 * Removes given figure from drawing
	 * @param f  the figure to remove from the drawing
	 */
	public void removeFromDrawing(Figure f) {
		currentDrawing.removeFigure(f);
	}
	
	/**
	 * Pass mouse pressed event to active drawing tool
	 * @param e  the mouse event
	 */
	void sendMousePressedToActiveTool(MouseEvent e) {
		if (activeTool != null)
			activeTool.mousePressedInDrawingArea(e);
		repaint();
	}
	
	/**
	 * Pass mouse released event to active drawing tool and
	 * repaint drawing
	 * @param e  the mouse event
	 */
	void sendMouseReleasedToActiveTool(MouseEvent e) {
		if (activeTool != null)
			activeTool.mouseReleasedInDrawingArea(e);
		repaint();
	}
	
	/**
	 * Pass mouse clicked event to active drawing tool
	 * @param e  the mouse event
	 */
	public void sendMouseClickedToActiveTool(MouseEvent e) {
		if (activeTool != null)
			activeTool.mouseClickedInDrawingArea(e);
	}
	
	/**
	 * Send mouse dragged event to active drawing tool and repaint
	 * @param e  the mouse event
	 */
	public void sendMouseDraggedToActiveTool(MouseEvent e) {
		if (activeTool != null)
			activeTool.mouseDraggedInDrawingArea(e);
		repaint();
	}
	
	/**
	 * Sets the given tool as the active tool
	 * @param aTool  the tool to activate
	 */
	public void setActiveTool(Tool aTool) {
		if (activeTool != null)
			activeTool.deactivate();
		aTool.activate();
		activeTool = aTool;
	}
	
	/**
	 * Gets figure at given point.
	 * @param point  the point at which figure is to be found
	 * @return  the figure at the given point or null if no such figure exists
	 */
	public Figure getFigureInDrawing(Point point) {
		return currentDrawing.getFigureAtPoint(point);
	}
	
	/**
	 * Helper to set up menus.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		JMenu drawingMenu = new JMenu("Drawing");
		JMenuItem drawingItem = new JMenuItem("New");
		drawingItem.addActionListener(new NewDrawingAction());
		drawingMenu.add(drawingItem);
		menuBar.add(drawingMenu);
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Helper to create tools.
	 */
	private void createTools() {
		JPanel toolArea = new JPanel();
		toolArea.setLayout(new GridLayout(0,1));
		toolArea.setSize(new Dimension(TOOL_WIDTH, HEIGHT));
		add(toolArea, BorderLayout.WEST);
		
		SelectionTool selecTool = new SelectionTool(this, toolArea);
		tools.add(selecTool);
		selecTool.activate();
		MoveTool moveTool = new MoveTool(this, toolArea);
		tools.add(moveTool);
		RectangleTool rectTool = new RectangleTool(this, toolArea);
		tools.add(rectTool);
		OvalTool ellipseTool = new OvalTool(this, toolArea);
		tools.add(ellipseTool);
	}
	
	/**
	 * Represents a listener that defines the action to be performed
	 * when the user wants to create a new drawing.
	 */
	private class NewDrawingAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			addNewDrawing();
		}
	}
	

	// Main application
	public static void main(String args[]) {
		new DrawingEditor();
	}
}

