package top.jotyy.koinpractice

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel
import top.jotyy.koinpractice.data.State
import top.jotyy.koinpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        observeData()
    }

    private fun observeData() {
        viewModel.state.observe(this, { state ->
            when (state) {
                State.Loading -> binding.progressView.visibility = View.VISIBLE
                State.Loaded -> binding.progressView.visibility = View.GONE
            }
        })

        viewModel.user.observe(this, { user ->
            binding.run {
                Picasso.get()
                    .load(user.avatarUrl)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(ivAvatar)
                tvName.text = user.name
                tvBio.text = user.bio
            }
        })

        viewModel.error.observe(this, { error ->
            Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT)
                .setAction("RETRY") {
                    viewModel.fetchUser()
                }
        })
    }
}
