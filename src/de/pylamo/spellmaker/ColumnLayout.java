package de.pylamo.spellmaker;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

class ColumnLayout
  implements LayoutManager2
{
  private final int margin_height;
  private final int margin_width;
  private final int spacing;
  private final int alignment;
  private static final int LEFT = 0;
  private static final int CENTER = 1;
  public static final int RIGHT = 2;
  
  public ColumnLayout(int spacing, int alignment)
  {
    this.margin_height = 5;
    this.margin_width = 5;
    this.spacing = spacing;
    this.alignment = alignment;
  }
  
  public ColumnLayout()
  {
    this(5, 0);
  }
  
  public void layoutContainer(Container parent)
  {
    Insets insets = parent.getInsets();
    Dimension parent_size = parent.getSize();
    
    int nkids = parent.getComponentCount();
    int x0 = insets.left + this.margin_width;
    
    int y = insets.top + this.margin_height;
    for (int i = 0; i < nkids; i++)
    {
      Component kid = parent.getComponent(i);
      if (kid.isVisible())
      {
        Dimension pref = kid.getPreferredSize();
        int x;
        switch (this.alignment)
        {
        case 0: 
        default: 
          x = x0;
          break;
        case 1: 
          x = (parent_size.width - pref.width) / 2;
          break;
        case 2: 
          x = parent_size.width - insets.right - this.margin_width - pref.width;
        }
        kid.setBounds(x, y, pref.width, pref.height);
        y += pref.height + this.spacing;
      }
    }
  }
  
  public Dimension preferredLayoutSize(Container parent)
  {
    return layoutSize(parent, 1);
  }
  
  public Dimension minimumLayoutSize(Container parent)
  {
    return layoutSize(parent, 2);
  }
  
  public Dimension maximumLayoutSize(Container parent)
  {
    return layoutSize(parent, 3);
  }
  
  Dimension layoutSize(Container parent, int sizetype)
  {
    int nkids = parent.getComponentCount();
    Dimension size = new Dimension(0, 0);
    Insets insets = parent.getInsets();
    int num_visible_kids = 0;
    for (int i = 0; i < nkids; i++)
    {
      Component kid = parent.getComponent(i);
      if (kid.isVisible())
      {
        num_visible_kids++;
        Dimension d;
        Dimension d;
        if (sizetype == 1)
        {
          d = kid.getPreferredSize();
        }
        else
        {
          Dimension d;
          if (sizetype == 2) {
            d = kid.getMinimumSize();
          } else {
            d = kid.getMaximumSize();
          }
        }
        if (d.width > size.width) {
          size.width = d.width;
        }
        size.height += d.height;
      }
    }
    size.width += insets.left + insets.right + 2 * this.margin_width;
    size.height += insets.top + insets.bottom + 2 * this.margin_height;
    if (num_visible_kids > 1) {
      size.height += (num_visible_kids - 1) * this.spacing;
    }
    return size;
  }
  
  public void addLayoutComponent(String constraint, Component comp) {}
  
  public void addLayoutComponent(Component comp, Object constraint) {}
  
  public void removeLayoutComponent(Component comp) {}
  
  public void invalidateLayout(Container parent) {}
  
  public float getLayoutAlignmentX(Container parent)
  {
    return 0.5F;
  }
  
  public float getLayoutAlignmentY(Container parent)
  {
    return 0.5F;
  }
}
