package swipe.android.hipaapix;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.util.Log;

import com.edbert.library.network.SocketOperator;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;

public class SmartImageDownloader extends BaseImageDownloader {


public SmartImageDownloader(Context context) {
    super(context);
}

@Override
public InputStream getStream(String imageUri, Object extra) throws IOException {
    switch (Scheme.ofUri(imageUri)) {
        case HTTP:
        case HTTPS:
       // 	Log.d("BEGIN STREAM", imageUri);
          //  KLog.e(TAG, "getStream: " + extra.toString(), KLog.Severity.WTF);
		InputStream s = null;
		try {
			s = SocketOperator.getInstance(null).getResponse(imageUri, APIManager.defaultSessionHeaders());
		//String string = SocketOperator.getInstance(null).httpGetRequest(imageUri, SessionManager.getInstance(this.context).defaultSessionHeaders());
		//
			//s = IOUtils.toInputStream(string, "UTF-8");
	//	s = new ByteArrayInputStream(string.getBytes());
			 		
					//new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
					
					
			
	
          /* HttpURLConnection conn = createConnection(imageUri, extra);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(getQuery((List<NameValuePair>)extra));
            writer.flush();
            writer.close();
            os.close();
*/

          
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return new BufferedInputStream(s, BUFFER_SIZE);
        case FILE:
            return getStreamFromFile(imageUri, extra);
        case CONTENT:
            return getStreamFromContent(imageUri, extra);
        case ASSETS:
            return getStreamFromAssets(imageUri, extra);
        case DRAWABLE:
            return getStreamFromDrawable(imageUri, extra);
        case UNKNOWN:
        default:
            return getStreamFromOtherSource(imageUri, extra);
    }
}

private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
{
    StringBuilder result = new StringBuilder();
    boolean first = true;

    for (NameValuePair pair : params)
    {
        if (first)
            first = false;
        else
            result.append("&");

        result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
        result.append("=");
        result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
    }

    return result.toString();
}
}