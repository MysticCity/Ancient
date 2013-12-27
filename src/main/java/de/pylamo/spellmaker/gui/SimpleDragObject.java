package de.pylamo.spellmaker.gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;

public class SimpleDragObject implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public final String s;
    public static DataFlavor dataFlavor;

    static {
        try {
            dataFlavor = new DataFlavor("text/uri-list;class=de.pylamo.spellmaker.gui.SimpleDragObject");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public SimpleDragObject(String s) {
        this.s = s;
    }

    public static class TransferableSimpleDragObject implements Transferable {

        private final SimpleDragObject s;

        public TransferableSimpleDragObject(SimpleDragObject pp) {
            this.s = pp;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{dataFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(dataFlavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {

            if (flavor.equals(dataFlavor)) {
                return s;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }
    }
}
