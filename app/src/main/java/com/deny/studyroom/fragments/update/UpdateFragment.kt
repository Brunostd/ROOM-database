package com.deny.studyroom.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.deny.studyroom.R
import com.deny.studyroom.ViewModel.UserViewModel
import com.deny.studyroom.data.User
import com.deny.studyroom.databinding.FragmentUpdateBinding
import kotlinx.android.synthetic.main.custom_row.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    lateinit var mUserViewModel: UserViewModel

    private var _binding: FragmentUpdateBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.updateFirstName.setText(args.currentUser.firstName)
        binding.updateLastName.setText(args.currentUser.lastName)
        binding.updateIdade.setText(args.currentUser.age.toString())

        binding.updateAddBtn.setOnClickListener(View.OnClickListener {
            updateItem()
        })

        setHasOptionsMenu(true)

        return view
    }

    fun updateItem(){
        var auxFirstName = binding.updateFirstName.text.toString()
        var auxLastName = binding.updateLastName.text.toString()
        var auxIdade = Integer.parseInt(binding.updateIdade.text.toString())

        if (inputCheck(auxFirstName, auxLastName, binding.updateIdade.text.toString())){
            //Create user object
            val updateUser = User(args.currentUser.id, auxFirstName, auxLastName, auxIdade)
            //Update current user
            mUserViewModel.updateUser(updateUser)
            Toast.makeText(requireContext(), "Sucesso ao atualizar", Toast.LENGTH_SHORT).show()
            //Navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else{
            Toast.makeText(requireContext(), "Sucesso ao atualizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean{
        return (!firstName.isEmpty() && !lastName.isEmpty() && !age.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete){
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(),
                "Removido com sucesso ${args.currentUser.firstName}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_, _ -> }
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Tem certeza de que deseja excluir ${args.currentUser.firstName}?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}