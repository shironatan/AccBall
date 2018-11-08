package websarvacom.wings.android.accball



import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),SensorEventListener,SurfaceHolder.Callback{

    private var surfaceWidth:Int = 0 //サーフェスビューの幅
    private var surfaceHeight:Int = 0 //サーフェスビューの高さ

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //SurfaceHolderインターフェイスのインスタンスを変数に取得
        val holder = surfaceView.holder
        //SurfaceHolderのaddCallbackメソッドを使って、サーフェスビューが変更、破棄された時のイベントリスナーを登録
        holder.addCallback(this)

    }

    //サーフェスが破棄された時の処理
    override fun surfaceDestroyed(holder:SurfaceHolder?) {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE)
                as SensorManager
        sensorManager.unregisterListener(this)
    }

    //サーフェスが作成された時の処理
    override fun surfaceCreated(holder: SurfaceHolder?) {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE)
                 as SensorManager
        val accSensor = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(
                this,accSensor,
                SensorManager.SENSOR_DELAY_GAME)
    }

    //サーフェスが変更された時の処理
    override fun surfaceChanged(holder: SurfaceHolder?,
                                format: Int, width: Int, height: Int) {
        surfaceWidth = width
        surfaceHeight = height
    }

    //センサーの値が更新された際に呼ばれます
    override fun onSensorChanged(event: SensorEvent?) {
        if(event == null) return
        if(event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            Log.d("MainActivity",
                    "x = ${event.values[0].toString()}" +
                            ",y = ${event.values[1].toString()}" +
                            ",z = ${event.values[2].toString()}")
        }
    }

    //センサーの制度が変更された場合に呼ばれます。
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}
