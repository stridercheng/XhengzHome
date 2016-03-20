package cn.com.chengz.xhengzhome.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * description:
 * User: stridercheng
 * Date: 2016-03-20
 * Time: 10:27
 * FIXME
 */
public class WbsUtils {

    public String getDataFromWbs(String url, String methodName, String serviceNameSpace) {
        String data = "";
        HttpTransportSE ht = new HttpTransportSE(url);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject object = new SoapObject(serviceNameSpace, methodName);

        //参数名不一定要与服务端的方法中的参数名相同，只要对应顺序相同即可；value参数指定参数值
        object.addProperty("id", 1);

        envelope.bodyOut = object;

        try {
            ht.call(null, envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            data = result.getProperty(0).toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return data;
    }
}  
