package com.example.yakovlevaan_01_02
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var shapes: Spinner
    private lateinit var formulaImage: ImageView
    private lateinit var editTextValue: EditText
    private lateinit var inputB: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        shapes = findViewById(R.id.shape)
        formulaImage = findViewById(R.id.formula)
        editTextValue = findViewById(R.id.editTextValue)
        inputB = findViewById(R.id.inputB)

        inputB.visibility = View.GONE

        val shapesArray = arrayOf("Круг", "Треугольник")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, shapesArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        shapes.adapter = adapter

        shapes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selected = parent.getItemAtPosition(position).toString()
                updateForShape(selected)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                formulaImage.setImageResource(0)
            }
        }
    }

    private fun updateForShape(shape: String) {
        when (shape) {
            "Круг" -> {
                formulaImage.setImageResource(R.drawable.circle)
                editTextValue.hint = "Введите длину окружности"
                inputB.visibility = View.GONE
            }
            "Треугольник" -> {
                formulaImage.setImageResource(R.drawable.tringle)
                editTextValue.hint = "Введите сторону a"
                inputB.visibility = View.VISIBLE
                inputB.hint = "Введите сторону b"
            }
        }
    }

    fun Count(view: View) {
        val selectedShape = shapes.selectedItem.toString()

        when (selectedShape) {
            "Круг" -> calculateCircle()
            "Треугольник" -> calculateTriangle()
            else -> showAlert("Ошибка", "Выберите фигуру")
        }
    }

    private fun calculateCircle() {
        val input = editTextValue.text.toString().trim()

        if (input.isEmpty()) {
            showAlert("Ошибка", "Введите длину окружности")
            return
        }

        try {
            val perimeter = input.toDouble()

            if (perimeter <= 0) {
                showAlert("Ошибка", "Длина окружности должна быть больше 0")
                return
            }

            val radius = perimeter / (2 * Math.PI)
            showAlert("Результат", "Радиус круга: ${String.format("%.2f", radius)}")

        } catch (e: NumberFormatException) {
            showAlert("Ошибка", "Введите корректное число")
        }
    }

    private fun calculateTriangle() {
        val inputA = editTextValue.text.toString().trim()
        val inputB = inputB.text.toString().trim()

        if (inputA.isEmpty() || inputB.isEmpty()) {
            showAlert("Ошибка", "Введите обе стороны треугольника")
            return
        }

        try {
            val a = inputA.toDouble()
            val b = inputB.toDouble()

            if (a <= 0 || b <= 0) {
                showAlert("Ошибка", "Стороны должны быть больше 0")
                return
            }

            if (2 * a < b){
                showAlert("Ошибка", "Такого треугольника не существует")
                return
            }


            val perimeter = 2 * a + b
            showAlert("Результат",
                "Периметр треугольника: ${String.format("%.2f", perimeter)}\n")

        } catch (e: NumberFormatException) {
            showAlert("Ошибка", "Введите корректные числа")
        }
    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}