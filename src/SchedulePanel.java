import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import javax.swing.JPanel;

public class SchedulePanel extends JPanel {
	private static final long serialVersionUID = -8215745499987889158L;
	private static final Color ONTIME_COLOR = new Color(160, 160, 192);
	private static final Color DELAYED_COLOR = new Color(192, 160, 160);
	private static final float FONT_HEIGHT = 0.25f;
	
	private Solution solution;
	private float w, h;

	public SchedulePanel(Solution solution) {
		super();
		this.solution = solution;
		Instance instance = solution.getInstance();
		h = instance.getQuayLength();
		w = -1;
		for (int i = 0; i < instance.getShipCount(); i++) {
			w = Math.max(w, solution.getBerthTime(i) + instance.getShip(i).getServingTime());
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.scale(getWidth() / w, getHeight() / h);
		g2d.setStroke(new BasicStroke(0));
		g2d.setFont(g2d.getFont().deriveFont(FONT_HEIGHT));
		for (int i = 0; i < solution.getInstance().getShipCount(); i++)
			drawShip(i, g2d);
	}
	
	private void drawShip(int i, Graphics2D g) {
		Ship shipI = solution.getInstance().getShip(i);
		int x = solution.getBerthTime(i);
		int y = solution.getPosition(i);
		int w = shipI.getServingTime();
		int h = shipI.getLength();
		int delay = x - shipI.getArrivalTime();
		g.setPaint(delay > 0 ? DELAYED_COLOR : ONTIME_COLOR);
		g.fillRect(x, y, w, h);
		g.setPaint(Color.BLACK);
		g.drawRect(x, y, w, h);
		String shipName = "S" + i;
		if (delay > 0)
			shipName += "(" + delay + ")";
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D.Float r = (Float) fm.getStringBounds(shipName, g);
		g.drawString(shipName, x + (w - r.width) / 2.0f, y + (h + FONT_HEIGHT) / 2.0f);
	}
}
