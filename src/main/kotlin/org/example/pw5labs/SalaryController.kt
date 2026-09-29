package org.example.pw5labs

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.DatePicker
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import java.time.LocalDate

class SalaryController {
    @FXML private lateinit var benefitMoneyField: TextField
    @FXML private lateinit var profitTaxField: TextField
    @FXML private lateinit var profTaxField: TextField
    @FXML private lateinit var retirementTaxField: TextField
    @FXML private lateinit var calendarDaysField: TextField
    @FXML private lateinit var workDaysField: TextField
    @FXML private lateinit var dateField: DatePicker
    @FXML private lateinit var totalPaidField: TextField
    @FXML private lateinit var paymentsTable: TableView<PaymentEntity>
    @FXML private lateinit var idColumn: TableColumn<PaymentEntity, Int>
    @FXML private lateinit var nameColumn: TableColumn<PaymentEntity, String>
    @FXML private lateinit var cityColumn: TableColumn<PaymentEntity, String>
    @FXML private lateinit var phoneColumn: TableColumn<PaymentEntity, String>
    @FXML private lateinit var positionColumn: TableColumn<PaymentEntity, String>
    @FXML private lateinit var salaryColumn: TableColumn<PaymentEntity, String>
    @FXML private lateinit var taxColumn: TableColumn<PaymentEntity, String>
    @FXML private lateinit var dateColumn: TableColumn<PaymentEntity, String>
    @FXML private lateinit var totalColumn: TableColumn<PaymentEntity, String>

    private val repository = PaymentRepository(DatabaseConnection())

    @FXML private fun initialize() {
        setupTable()
        setDefaultValues()
        loadPayments()
    }

    @FXML private fun countSalaries() {
        try {
            repository.calculate(SalaryInput.fromFields(
                benefitMoneyField.text, profitTaxField.text, profTaxField.text,
                retirementTaxField.text, calendarDaysField.text, workDaysField.text, dateField.value
            ))
            loadPayments()
        } catch (error: Exception) {
            ErrorWindow().show("Расчёт зарплаты", error.message ?: "Не удалось рассчитать зарплаты")
        }
    }

    private fun setupTable() {
        idColumn.cellValueFactory = PropertyValueFactory("id")
        nameColumn.cellValueFactory = PropertyValueFactory("name")
        cityColumn.cellValueFactory = PropertyValueFactory("city")
        phoneColumn.cellValueFactory = PropertyValueFactory("phone")
        positionColumn.cellValueFactory = PropertyValueFactory("position")
        salaryColumn.cellValueFactory = PropertyValueFactory("salary")
        taxColumn.cellValueFactory = PropertyValueFactory("tax")
        dateColumn.cellValueFactory = PropertyValueFactory("date")
        totalColumn.cellValueFactory = PropertyValueFactory("total")
    }

    private fun setDefaultValues() {
        dateField.value = LocalDate.now()
        benefitMoneyField.text = "10"
        profitTaxField.text = "10"
        profTaxField.text = "1"
        retirementTaxField.text = "10"
        calendarDaysField.text = "30"
        workDaysField.text = "22"
    }

    private fun loadPayments() {
        try {
            val payments = repository.findAll()
            paymentsTable.items = FXCollections.observableArrayList(payments)
            totalPaidField.text = Money.format(payments.sumOf { it.totalValue })
        } catch (error: Exception) {
            paymentsTable.items = FXCollections.observableArrayList()
            totalPaidField.text = Money.format(0.0)
            ErrorWindow().show("Подключение к PostgreSQL", "Запустите Docker: docker compose up -d\n\n${error.message}")
        }
    }
}
