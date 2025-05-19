import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fundra.databinding.ActivityFirstProjectBinding
import com.example.fundra.databinding.InvestProjectsBinding

class Invest_Projects : AppCompatActivity() {
    private lateinit var binding: InvestProjectsBinding
    private var totalAmount = 350000
    private var totalDonors = 799
    private val goalAmount = 500000
    private var isSaved = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InvestProjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}