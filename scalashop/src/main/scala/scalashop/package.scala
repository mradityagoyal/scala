
import common._

package object scalashop {

  /** The value of every pixel is represented as a 32 bit integer. */
  type RGBA = Int

  /** Returns the red component. */
  def red(c: RGBA): Int = (0xff000000 & c) >>> 24

  /** Returns the green component. */
  def green(c: RGBA): Int = (0x00ff0000 & c) >>> 16

  /** Returns the blue component. */
  def blue(c: RGBA): Int = (0x0000ff00 & c) >>> 8

  /** Returns the alpha component. */
  def alpha(c: RGBA): Int = (0x000000ff & c) >>> 0

  /** Used to create an RGBA value from separate components. */
  def rgba(r: Int, g: Int, b: Int, a: Int): RGBA = {
    (r << 24) | (g << 16) | (b << 8) | (a << 0)
  }

  /** Restricts the integer into the specified range. */
  def clamp(v: Int, min: Int, max: Int): Int = {
    if (v < min) min
    else if (v > max) max
    else v
  }

  /** Image is a two-dimensional matrix of pixel values. */
  class Img(val width: Int, val height: Int, private val data: Array[RGBA]) {
    def this(w: Int, h: Int) = this(w, h, new Array(w * h))
    def apply(x: Int, y: Int): RGBA = data(y * width + x)
    def update(x: Int, y: Int, c: RGBA): Unit = data(y * width + x) = c
  }

  /** Computes the blurred RGBA value of a single pixel of the input image. */
  def boxBlurKernel(src: Img, x: Int, y: Int, radius: Int): RGBA = {
    val pixels = for (
      xOffset <- -radius to radius if (x + xOffset == clamp(x + xOffset, 0, src.width - 1));
      yOffset <- -radius to radius if (y + yOffset == clamp(y + yOffset, 0, src.height - 1))
    ) yield (
      src(x + xOffset, y + yOffset))

    rgba(
      pixels.map(red(_)).sum / pixels.length,
      pixels.map(green(_)).sum / pixels.length,
      pixels.map(blue(_)).sum / pixels.length,
      pixels.map(alpha(_)).sum / pixels.length)
  }

  /*
     * Using while loops 
  def boxBlurKernel(src: Img, x: Int, y: Int, radius: Int): RGBA = {
    var blurredPix: RGBA = 0
    var numPix = 0
    var yOffset = -radius
    while (yOffset <= radius) {
      var xOffset = -radius
      while (xOffset <= radius) {
        if (x + xOffset == clamp(x + xOffset, 0, src.width -1) && y + yOffset == clamp(y + yOffset, 0, src.height - 1)) {
          val pixel: RGBA = src(clamp(x + xOffset, 0, src.width - 1), clamp(y + yOffset, 0, src.height - 1))
          blurredPix = addRGBA(blurredPix, pixel)
          numPix = numPix + 1
        }
        xOffset = xOffset + 1
      }
      yOffset = yOffset + 1
    }
    rgba(red(blurredPix) / numPix, green(blurredPix) / numPix, blue(blurredPix) / numPix, alpha(blurredPix) / numPix)
  }
  * */

  /*
    * using yield but no fold 
    *  def boxBlurKernel(src: Img, x: Int, y: Int, radius: Int): RGBA = {
    var blurredPix: RGBA = rgba(0, 0, 0, 0)
    for (xOffset <- -radius to radius; yOffset <- -radius to radius) {
      blurredPix = addRGBA(blurredPix, src(clamp(x + xOffset, 0, src.width), clamp(y + yOffset, 0, src.height)))
    }
    val numPix = Math.pow(radius * 2 + 1, 2).toInt
    rgba(red(blurredPix) / numPix, green(blurredPix) / numPix, blue(blurredPix) / numPix, alpha(blurredPix) / numPix)
  }
    */

}
