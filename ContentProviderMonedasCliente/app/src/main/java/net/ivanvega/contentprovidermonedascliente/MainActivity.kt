package net.ivanvega.contentprovidermonedascliente

import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader

class MainActivity : AppCompatActivity() {

    val mLoaderCallbacks = object : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            //TODO("Not yet implemented")
            return CursorLoader(
                applicationContext,
                Uri.parse("content://net.ivanvega.proyectodivisacontentprividera/monedas"),
                arrayOf<String>("_ID", "codeMoneda","nombreMoneda"),
                null, null, null)
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {
            //TODO("Not yet implemented")
            Log.i("MONEDAXCliente", " Nada de CP")
        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            //TODO("Not yet implemented")

            data?.apply {
                val adapter = SimpleCursorAdapter(applicationContext,
                    android.R.layout.simple_list_item_2,
                    this,
                    arrayOf<String>("_ID", "codeMoneda","nombreMoneda"),
                    IntArray(2).apply {
                                      set(0, android.R.id.text1)
                        set(1, android.R.id.text2)
                    } ,
                    SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE
                )
                spn.adapter = adapter
                while (moveToNext()){
                    Log.i("MONEDAXClienteLC", " id: ${getInt(0)} , code: ${getString(1)}, moneda ${getString(2)}")
                }
            }

        }

    }
    lateinit var spn : Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spn = findViewById(R.id.spinner)


        LoaderManager.getInstance(this)
            .initLoader<Cursor>(1001, null, mLoaderCallbacks)
        var   micursor   = contentResolver.query(
            Uri.parse("content://net.ivanvega.proyectodivisacontentprividera/monedas"),
            arrayOf<String>("_ID", "codeMoneda","nombreMoneda"),
            null, null,null
        )
        micursor?.apply {
            while (moveToNext()){
                Log.i("MONEDAXCliente", " id: ${getInt(0)} , code: ${getString(1)}, moneda ${getString(2)}")
            }
        }


    }
}