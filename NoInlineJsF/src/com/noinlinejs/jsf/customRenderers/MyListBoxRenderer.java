package com.noinlinejs.jsf.customRenderers;


import java.io.IOException;

import javax.faces.context.ResponseWriter;

/**
 * <B>ListRenderer</B> is a class that renders the current value of
 * <code>UISelectOne<code> or <code>UISelectMany<code> component as a list of
 * options.
 */

public class MyListBoxRenderer extends MyMenuRenderer {

    // ------------------------------------------------------- Protected Methods


    @Override
    protected void writeDefaultSize(ResponseWriter writer, int itemCount)
          throws IOException {

        // If size not specified, default to number of items
        writer.writeAttribute("size", itemCount, "size");

    }

} // end of class ListboxRenderer