package com.deny.studyroom.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.deny.studyroom.R
import com.deny.studyroom.ViewModel.UserViewModel
import com.deny.studyroom.data.User
import com.deny.studyroom.databinding.FragmentAddBinding
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private var _binding: FragmentAddBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root


        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.addBtn.setOnClickListener(View.OnClickListener {
            insertDataToDatabase()
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun insertDataToDatabase() {
        val auxFirstName = binding.firstName.text.toString()
        val auxLastName = binding.lastName.text.toString()
        val auxIdade = binding.idade.text.toString()

        if (inputCheck(auxFirstName, auxLastName, auxIdade)){
            val user = User(0, auxFirstName, auxLastName, Integer.parseInt(auxIdade))

            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Sucesso ao adicionar", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else{
            Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean{
        return (!firstName.isEmpty() && !lastName.isEmpty() && !age.isEmpty())
    }

}