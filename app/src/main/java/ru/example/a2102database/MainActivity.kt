package ru.example.a2102database

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.example.a2102database.ui.theme._2102DataBaseTheme

class MainActivity : ComponentActivity() {
    init {
        context = this
    }
    companion object{
        private var context:MainActivity? = null
        fun getContext(): Context {
            return context!!.applicationContext
        }
    }
    val arrayMockBelka = ArrayList<Belka>().apply {
       add(Belka( colorTail = "Black", name= "Murka"))
        add(Belka(colorTail = "Grey", name= "Chuya"))
    }
    val belkaMutableState = MutableStateFlow<List<Belka>>(arrayMockBelka)
    val belkaState = belkaMutableState.asStateFlow()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _2102DataBaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        var belkaText by remember { mutableStateOf("") }
        var belkaId by remember { mutableStateOf("") }
        var belkaColor by remember { mutableStateOf("") }

        var arrayBelka = belkaState.collectAsState().value
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Text(text = "id")
            TextField(
                value = belkaId,
                onValueChange = {it -> belkaId=it})
            Text(text = "name")
            TextField(
                value = belkaText,
                onValueChange = {it -> belkaText=it})
            Text(text = "Color")
            TextField(
                value = belkaColor,
                onValueChange = {it -> belkaColor=it})
            Button(onClick = {
                GlobalScope.launch {
                    SingletonBD.bd.belkaDao().insertBelka(Belka(0, belkaColor, belkaText))
                    belkaMutableState.value = SingletonBD.bd.belkaDao().getAllBelka()
                }

            }) {
                Text(text = "Insert Belka")
            }
            Button(onClick = {
                GlobalScope.launch {
                    SingletonBD.bd.belkaDao().deleteBelka(Belka(belkaId.toInt(), belkaColor, belkaText))
                    belkaMutableState.value = SingletonBD.bd.belkaDao().getAllBelka()
                }

            }) {
                Text(text = "Delete Belka")
            }
            Button(onClick = {
                GlobalScope.launch {
                    SingletonBD.bd.belkaDao().updBelka(Belka(belkaId.toInt(), belkaColor, belkaText))
                    belkaMutableState.value = SingletonBD.bd.belkaDao().getAllBelka()
                }

            }) {
                Text(text = "Update Belka")
            }

            LazyColumn{
                items(arrayBelka){belka ->
                    Card(
                        modifier = Modifier.size(200.dp, 100.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(text = belka.id.toString())
                            Text(text = belka.name)
                            Text(text = belka.colorTail)
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        _2102DataBaseTheme {
            Greeting("Android")
        }
    }
}




