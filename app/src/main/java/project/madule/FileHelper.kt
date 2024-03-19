package project.madule

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.powrolx.saramahalle.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink
import project.main.G
import project.main.G.Companion.context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class FileHelper {
  companion object {
    fun internal(directory: String): File {
      val path = G.context.getDir(directory, Context.MODE_PRIVATE).absolutePath
      val dir = File(path)
      Debug.info(dir)
      mkdirs(dir)
      return dir
    }


    fun external(directory: String): File {
      val path = G.context.getExternalFilesDir(directory)!!.absolutePath
      val dir = File(path)
      mkdirs(dir)
      return dir
    }


    fun mkdirs(dir: File) {
      if (!dir.exists()) {
        dir.mkdirs()
      }
    }


    fun download(url: String, dir: String, filename: String) = download(url, internal(dir), filename)


    fun download(url: String, dir: File, filename: String) {
      val client = OkHttpClient()
      val request = Request.Builder().url(url).build()

      val response = client.newCall(request).execute()
      val contentType = response.header("content-type", null)
      var ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(contentType)

      val file = File(dir.absolutePath, filename)


      val body = response.body
      val sink = file.sink().buffer()

      body?.source().use { input ->
        sink.use { output ->
          output.writeAll(input!!)
        }
      }
    }


    @JvmStatic
    fun unzip(zipFile: String, dest: File) {
      val buffer = ByteArray(1024)
      val zis = ZipInputStream(FileInputStream(zipFile))
      var zipEntry = zis.getNextEntry()
      while (zipEntry != null) {
        val newFile = newFile(dest, zipEntry)
        val fos = FileOutputStream(newFile)
        var len: Int
        do {
          len = zis.read(buffer)
          if (len <= 0) {
            break
          }
          fos.write(buffer, 0, len)
        } while (len > 0)
        fos.close()
        zipEntry = zis.getNextEntry()
      }
      zis.closeEntry()
      zis.close()
    }


    fun newFile(destinationDir: File, zipEntry: ZipEntry): File {
      val destFile = File(destinationDir, zipEntry.getName())

      val destDirPath = destinationDir.canonicalPath
      val destFilePath = destFile.canonicalPath

      if (!destFilePath.startsWith(destDirPath + File.separator)) {
        throw IOException("Entry is outside of the target dir: " + zipEntry.getName())
      }

      return destFile
    }



    fun installApk(address: File) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", address)
        val install = Intent(Intent.ACTION_VIEW)
        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        install.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
        install.data = contentUri
        G.currentActivity.startActivity(install)
        G.currentActivity.finish()
      } else {
        val install = Intent(Intent.ACTION_VIEW)
        install.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        install.setDataAndType(Uri.fromFile(address), "application/vnd.android.package-archive")
        G.currentActivity.startActivity(install)
        G.currentActivity.finish()
      }
    }
  }
}