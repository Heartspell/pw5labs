package org.example.pw5labs.qrcode

import javafx.embed.swing.SwingFXUtils
import javafx.fxml.FXML
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.example.pw5labs.common.ErrorWindow
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class QrCodeController {
    @FXML private lateinit var initialField1: TextField
    @FXML private lateinit var initialField2: TextField
    @FXML private lateinit var qrCodeImage: ImageView

    @FXML
    private fun createQrCode() {
        val value = initialField1.text.trim()
        if (value.isEmpty()) {
            ErrorWindow().show("Генератор штрих-кода", "Введите текст для штрих-кода")
            return
        }
        if (value.any { it.code !in 32..126 }) {
            ErrorWindow().show("Генератор штрих-кода", "Code 128 поддерживает латинские символы и цифры")
            return
        }

        try {
            val file = barcodeFile()
            file.parentFile.mkdirs()
            ImageIO.write(createBarcode(value), "png", file)
            qrCodeImage.image = SwingFXUtils.toFXImage(ImageIO.read(file), null)
            initialField2.text = file.nameWithoutExtension
        } catch (error: Exception) {
            ErrorWindow().show("Генератор штрих-кода", error.message ?: "Не удалось создать штрих-код")
        }
    }

    @FXML
    private fun showQrCode() {
        try {
            val file = barcodeFile()
            require(file.isFile) { "Файл ${file.name} не найден в папке generated-barcodes" }
            qrCodeImage.image = Image(file.toURI().toString())
        } catch (error: Exception) {
            ErrorWindow().show("Генератор штрих-кода", error.message ?: "Не удалось открыть штрих-код")
        }
    }

    private fun barcodeFile(): File {
        val name = initialField2.text.trim().ifEmpty { initialField1.text.trim() }
        require(name.matches(Regex("[A-Za-z0-9_-]+"))) {
            "Название файла должно содержать только латинские буквы, цифры, _ или -"
        }
        return File("generated-barcodes", "$name.png")
    }

    private fun createBarcode(value: String): BufferedImage {
        val codes = mutableListOf(START_B)
        value.forEach { codes += it.code - 32 }
        var checksum = START_B
        value.forEachIndexed { index, character -> checksum += (index + 1) * (character.code - 32) }
        codes += checksum % 103
        codes += STOP

        val scale = 3
        val quietZone = 10
        val moduleWidth = codes.sumOf { CODE_PATTERNS[it].sumOf(Char::digitToInt) }
        val image = BufferedImage((moduleWidth + quietZone * 2) * scale, 180, BufferedImage.TYPE_INT_RGB)
        val graphics = image.createGraphics()
        drawBarcode(graphics, codes, scale, quietZone)
        graphics.dispose()
        return image
    }

    private fun drawBarcode(graphics: Graphics2D, codes: List<Int>, scale: Int, quietZone: Int) {
        graphics.color = Color.WHITE
        graphics.fillRect(0, 0, graphics.deviceConfiguration.bounds.width, 180)
        graphics.color = Color.BLACK
        var x = quietZone * scale
        codes.forEach { code ->
            CODE_PATTERNS[code].forEachIndexed { index, widthChar ->
                val width = widthChar.digitToInt() * scale
                if (index % 2 == 0) graphics.fillRect(x, 15, width, 125)
                x += width
            }
        }
    }

    private companion object {
        const val START_B = 104
        const val STOP = 106
        val CODE_PATTERNS = listOf(
            "212222", "222122", "222221", "121223", "121322", "131222", "122213", "122312", "132212", "221213",
            "221312", "231212", "112232", "122132", "122231", "113222", "123122", "123221", "223211", "221132",
            "221231", "213212", "223112", "312131", "311222", "321122", "321221", "312212", "322112", "322211",
            "212123", "212321", "232121", "111323", "131123", "131321", "112313", "132113", "132311", "211313",
            "231113", "231311", "112133", "112331", "132131", "113123", "113321", "133121", "313121", "211331",
            "231131", "213113", "213311", "213131", "311123", "311321", "331121", "312113", "312311", "332111",
            "314111", "221411", "431111", "111224", "111422", "121124", "121421", "141122", "141221", "112214",
            "112412", "122114", "122411", "142112", "142211", "241211", "221114", "413111", "241112", "134111",
            "111242", "121142", "121241", "114212", "124112", "124211", "411212", "421112", "421211", "212141",
            "214121", "412121", "111143", "111341", "131141", "114113", "114311", "411113", "411311", "113141",
            "114131", "311141", "411131", "211412", "211214", "211232", "2331112"
        )
    }
}
