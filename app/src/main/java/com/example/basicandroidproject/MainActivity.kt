package com.example.basicandroidproject

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.basicandroidproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mViewModel : BaseViewModel by viewModels()

    private var TAG: String = "terry - MainActivity"
    private lateinit var nfcPendingIntent: PendingIntent
    private lateinit var nfcAdapter: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcPendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE
        )
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, null, null);
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableForegroundDispatch(this);
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        try {

            Log.d(TAG, "onNewIntent: ${intent.action}")
        if (intent.action == NfcAdapter.ACTION_TAG_DISCOVERED){
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { messages ->
                for (i in messages.indices) showMsg(messages[i] as NdefMessage)
            }

        }else{
            val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            parseNdefMessage(intent)
            Log.d(TAG, "onNewIntent: ${tag.toString()}")
//            val detectedTag : Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//            val writeValue : String = "아이폰 사고싶다🍟🍖";
//            val message: NdefMessage = createTagMessage(writeValue);
//
//            detectedTag?.let { writeTag(message, it) };
        }
        }catch (e : Exception){
            Log.d(TAG, "onNewIntent: ${e.message}")
        }

    }

    fun parseNdefMessage(intent: Intent) {
        val ndefMessageArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        val ndefMessage = ndefMessageArray!![0] as NdefMessage
        val msg = String(ndefMessage.records[0].payload)
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        Toast.makeText(
            applicationContext, "Here is my text",
            Toast.LENGTH_LONG
        ).show()

        //editText = (EditText) findViewById(R.id.editText);
        //String text = editText.getText().toString();
//        editText.setText(msg) //my attempt to set my received data to "editText" field
    }
    private fun createTagMessage(msg: String): NdefMessage {
        return NdefMessage(NdefRecord.createUri(msg))
    }

    fun writeTag(message: NdefMessage, tag: Tag) {
        val size = message.toByteArray().size
        try {
            val ndef = Ndef.get(tag)
            if (ndef != null) {
                ndef.connect()
                if (!ndef.isWritable) {
                    Toast.makeText(applicationContext, "can not write NFC tag", Toast.LENGTH_SHORT).show()
                }
                if (ndef.maxSize < size) {
                    Toast.makeText(applicationContext, "NFC tag size too large", Toast.LENGTH_SHORT).show()
                }
                ndef.writeNdefMessage(message)
                Toast.makeText(applicationContext, "NFC tag is writted", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.message?.let { Log.i(TAG, it) };
        }
    }

    fun showMsg(mMessage: NdefMessage) {
        val recs = mMessage.records
        for (i in recs.indices) {
            val record = recs[i]
            if (Arrays.equals(record.type, NdefRecord.RTD_URI)) {
                val u: Uri = record.toUri()
                val j = Intent(Intent.ACTION_VIEW)
                j.data = u
                startActivity(j)
                finish()
            }
        }
    }
}