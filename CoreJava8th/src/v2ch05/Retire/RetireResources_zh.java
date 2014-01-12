package v2ch05.Retire;
import java.awt.*;

/**
 * These are the Chinese non-string resources for the retirement calculator.
 * @version 1.21 2001-08-27
 * @author Cay Horstmann
 */
public class RetireResources_zh extends java.util.ListResourceBundle
{
   public Object[][] getContents()
   {
      return contents;
   }

   static final Object[][] contents = {
   // BEGIN LOCALIZE
         { "colorPre", Color.red }, { "colorGain", Color.blue }, { "colorLoss", Color.yellow }
   // END LOCALIZE
   };
}
