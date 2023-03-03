package net.ivanvega.proyectodivisacontentprividera

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.app.LoaderManager.LoaderCallbacks
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import net.ivanvega.proyectodivisacontentprividera.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {


    val mLoaderCallbacks = object : LoaderCallbacks<Cursor>{
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            //TODO("Not yet implemented")
            return CursorLoader(
                context!!.applicationContext,
                Uri.parse("content://net.ivanvega.proyectodivisacontentprividera/monedas"),
                arrayOf<String>("_ID", "codeMoneda","nombreMoneda"),
                null, null, null)
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {
            //TODO("Not yet implemented")
            Log.i("MONEDAXCP", " Nada de CP")
        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            //TODO("Not yet implemented")
            data?.apply {
                while (moveToNext()){
                    Log.i("MONEDAXCP", " id: ${getInt(0)} , code: ${getString(1)}, moneda ${getString(2)}")
                }
            }

        }

    }

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        LoaderManager.getInstance(this)
                .initLoader<Cursor>(1001, null, mLoaderCallbacks)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}