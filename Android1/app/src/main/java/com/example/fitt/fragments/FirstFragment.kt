package com.example.fitt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.fitt.R
import com.example.fitt.adapter.MainAdapter
import com.example.fitt.data.ReminderData
import com.example.fitt.repository.ReminderRepository
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment(), MainAdapter.OnClickReminderListener {

    private lateinit var mainAdapter: MainAdapter

    private lateinit var textViewNoReminders: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewNoReminders = view.findViewById(R.id.textViewNoReminders)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.recyclerViewReminders)

        recyclerView.addItemDecoration(
                DividerItemDecoration(
                        view.context,
                        DividerItemDecoration.VERTICAL
                )
        )

        mainAdapter = MainAdapter(this, ReminderRepository.getMockRepository())
        recyclerView.adapter = mainAdapter
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onClick(reminderData: ReminderData) {

    }
}