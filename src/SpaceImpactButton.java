import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class SpaceImpactButton extends JButton {
    private boolean over;
    private Color color;
    private Color colorOver;
    private Color colorClick;
    private Color borderColor;
    private int radius = 0;
    private String text;

    private final Font font = new Font("Race Sport", Font.PLAIN, 25);

    public SpaceImpactButton(String text) {
        super();
        this.text = text;
        setFont(font);
        setFocusPainted(false);
        setBackground(new Color(0, 0, 0, 0));
        setContentAreaFilled(false);

        color = new Color(0, 0, 0, 255);
        colorOver = new Color(51, 204, 255, 255);
        colorClick = new Color(152, 184, 144, 255);
        borderColor = new Color(0, 0, 0, 255);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(colorOver);
                over = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(color);
                over = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(colorClick);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(over ? colorOver : color);
            }
        });
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColorOver() {
        return colorOver;
    }

    public void setColorOver(Color colorOver) {
        this.colorOver = colorOver;
    }

    public Color getColorClick() {
        return colorClick;
    }

    public void setColorClick(Color colorClick) {
        this.colorClick = colorClick;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint border
        g2D.setColor(borderColor);
        g2D.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Paint button background
        g2D.setColor(getBackground());
        g2D.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, radius, radius);

        // Paint text
        g2D.setColor(Color.WHITE);
        g2D.setFont(font);
        int textWidth = g.getFontMetrics().stringWidth(text);
        int textHeight = g.getFontMetrics().getAscent();
        g2D.drawString(text, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - 4);
    }

    @Override
    public void setText(String text) {
        this.text = text;
        repaint();
    }
}
