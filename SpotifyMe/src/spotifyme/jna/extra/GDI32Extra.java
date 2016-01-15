package spotifyme.jna.extra;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HDC;
import com.sun.jna.win32.W32APIOptions;


public interface GDI32Extra extends GDI32 {

	GDI32Extra INSTANCE = (GDI32Extra) Native.loadLibrary("gdi32", GDI32Extra.class, W32APIOptions.DEFAULT_OPTIONS);
	
	/**
	 * The BitBlt function performs a bit-block transfer of the color data corresponding to a rectangle of pixels from the specified source device context into a destination device context.
	 * @param hObject A handle to the destination device context.
	 * @param nXDest The x-coordinate, in logical units, of the upper-left corner of the destination rectangle.
	 * @param nYDest The y-coordinate, in logical units, of the upper-left corner of the destination rectangle.
	 * @param nWidth The width, in logical units, of the source and destination rectangles.
	 * @param nHeight The height, in logical units, of the source and the destination rectangles.
	 * @param hObjectSource A handle to the source device context.
	 * @param nXSrc The x-coordinate, in logical units, of the upper-left corner of the source rectangle.
	 * @param nYSrc The y-coordinate, in logical units, of the upper-left corner of the source rectangle.
	 * @param dwRop A raster-operation code. These codes define how the color data for the source rectangle is to be combined with the color data for the destination rectangle to achieve the final color.
	 * @return
	 * 	If the function succeeds, the return value is nonzero.
	 * 	If the function fails, the return value is zero. To get extended error information, call GetLastError.
	 */
	public boolean BitBlt(HDC hObject, int nXDest, int nYDest, int nWidth, int nHeight, HDC hObjectSource, int nXSrc, int nYSrc, DWORD dwRop);
	
}
