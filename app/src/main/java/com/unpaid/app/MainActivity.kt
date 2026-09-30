package com.unpaid.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Invoice(
    val customer: String,
    val invoiceNumber: String,
    val amount: Double,
    val dueDate: String,
    val status: String
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UnpaidApp()
        }
    }
}

@Composable
fun UnpaidApp() {

    val invoices = remember {
        mutableStateListOf(
            Invoice(
                customer = "Müller GmbH",
                invoiceNumber = "RE-2026-101",
                amount = 850.00,
                dueDate = "15.09.2026",
                status = "OVERDUE"
            ),
            Invoice(
                customer = "Berlin Café",
                invoiceNumber = "RE-2026-102",
                amount = 620.00,
                dueDate = "03.10.2026",
                status = "DUE SOON"
            ),
            Invoice(
                customer = "Event Service Berlin",
                invoiceNumber = "RE-2026-103",
                amount = 1200.00,
                dueDate = "20.09.2026",
                status = "PAID"
            )
        )
    }

    val overdue = invoices
        .filter { it.status == "OVERDUE" }
        .sumOf { it.amount }

    val dueSoon = invoices
        .filter { it.status == "DUE SOON" }
        .sumOf { it.amount }

    val paid = invoices
        .filter { it.status == "PAID" }
        .sumOf { it.amount }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(20.dp)
    ) {

        Text(
            text = "UNPAID",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Payment & Invoice Tracker",
            color = Color(0xFFAAAAAA),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        SummaryCard(
            title = "OVERDUE",
            amount = overdue,
            color = Color(0xFFFF5252)
        )

        Spacer(modifier = Modifier.height(10.dp))

        SummaryCard(
            title = "DUE THIS WEEK",
            amount = dueSoon,
            color = Color(0xFFFFB300)
        )

        Spacer(modifier = Modifier.height(10.dp))

        SummaryCard(
            title = "PAID",
            amount = paid,
            color = Color(0xFF66BB6A)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Invoices",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = {
                    // Invoice adding screen will be added next
                }
            ) {
                Text("+ Add Invoice")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(invoices) { invoice ->
                InvoiceCard(invoice)
            }
        }
    }
}

@Composable
fun SummaryCard(
    title: String,
    amount: Double,
    color: Color
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1C1C1C)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text = title,
                    color = color,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "€%.2f".format(amount),
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun InvoiceCard(invoice: Invoice) {

    val statusColor = when (invoice.status) {
        "OVERDUE" -> Color(0xFFFF5252)
        "DUE SOON" -> Color(0xFFFFB300)
        "PAID" -> Color(0xFF66BB6A)
        else -> Color.White
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A)
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = invoice.customer,
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = invoice.status,
                    color = statusColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = invoice.invoiceNumber,
                color = Color(0xFF999999),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row {

                Text(
                    text = "€%.2f".format(invoice.amount),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Due: ${invoice.dueDate}",
                    color = Color(0xFFBBBBBB),
                    fontSize = 13.sp
                )
            }

            if (invoice.status == "OVERDUE") {

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = {
                        // Payment reminder will be added next
                    }
                ) {
                    Text(
                        text = "Send Payment Reminder",
                        color = Color(0xFF8BC34A)
                    )
                }
            }
        }
    }
}
