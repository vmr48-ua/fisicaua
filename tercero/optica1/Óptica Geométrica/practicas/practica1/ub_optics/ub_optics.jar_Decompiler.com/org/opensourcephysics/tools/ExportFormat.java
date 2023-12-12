package org.opensourcephysics.tools;

import java.io.File;
import java.util.List;

public interface ExportFormat {
   String description();

   String extension();

   void export(File var1, List var2);
}
