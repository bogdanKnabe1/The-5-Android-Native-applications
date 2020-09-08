package com.example.fitt.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitt.R
import com.example.fitt.adapter.MainAdapter
import com.example.fitt.viewmodel.ReminderViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*

class FirstFragment : Fragment() {

    private lateinit var reminderViewModel: ReminderViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        // Recyclerview
        val adapter = MainAdapter()
        val recyclerView = view.recyclerViewReminders
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //adding item decoration
        recyclerView.addItemDecoration(
                DividerItemDecoration(
                        view.context,
                        DividerItemDecoration.VERTICAL
                )
        )

        //manage visibility
        recyclerView.visibility = View.VISIBLE

        // UserViewModel
        reminderViewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)
        reminderViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
        })

        //transition to another fragment
        view.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_settings -> Toast.makeText(activity, "SETTINGS", Toast.LENGTH_SHORT).show()
            R.id.action_menu_delete -> deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    //ALERT dialog to delete reminder
    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            reminderViewModel.deleteAllUsers()
            Toast.makeText(
                    requireContext(),
                    "Successfully removed everything",
                    Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }

}