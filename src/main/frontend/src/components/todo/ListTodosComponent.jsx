import React, {Component} from "react";
import ExpensesService from "../../api/todo/ExpensesService.js";
import moment from "moment";

class ListTodosComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            actionMessage: null,
            expenses: []
        }
        this.refreshExpenses = this.refreshExpenses.bind(this)
        this.deleteExpense = this.deleteExpense.bind(this)
        this.createExpense = this.createExpense.bind(this)
    }

    componentDidMount() {
        this.refreshExpenses();
    }

    refreshExpenses() {
        ExpensesService.getAllExpenses()
            .then(
                response => {
                    this.setState({expenses: response.data})
                }
            )
    }

    deleteExpense(expenseId) {
        ExpensesService.deleteExpense(expenseId)
            .then(
                response => {
                    this.setState({actionMessage: response.data})
                    this.refreshExpenses()
                }
            )
    }

    updateExpense(expenseId) {
        this.props.history.push(`/todo/${expenseId}`)
    }

    createExpense() {
        this.props.history.push(`/todo/0`)
    }

    render() {
        return (
            <div>
                <h1>List Expenses</h1>
                {this.state.actionMessage && <div className="alert alert-success">{this.state.actionMessage}</div>}
                <div className="container">
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Nome / Descrição</th>
                            <th>Valor</th>
                            <th>Categoria</th>
                            <th>Total de Parcelas</th>
                            <th>Parcela Atual</th>
                            <th>Frequência (dias)</th>
                            <th>Data de Criação</th>
                            <th>Última Modificação</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.expenses.map(
                                expense =>
                                    <tr key={expense.expenseId}>
                                        <td>{expense.expenseName}</td>
                                        <td>{expense.amount}</td>
                                        <td>{expense.category}</td>
                                        <td>{expense.totalParcels}</td>
                                        <td>{expense.currentParcel}</td>
                                        <td>{expense.frequency}</td>
                                        <td>{moment(expense.expenseCreatedDate).format("DD-MM-YYYY hh:mm")}</td>
                                        <td>{moment(expense.expenseLastUpdatedDate).format("DD-MM-YYYY hh:mm")}</td>
                                        <td>
                                            <button className="btn btn-warning"
                                                    onClick={() => this.updateExpense(expense.expenseId)}>Edit
                                            </button>
                                        </td>
                                        <td>
                                            <button className="btn btn-danger btn-circle"
                                                    onClick={() => this.deleteExpense(expense.expenseId)}>X
                                            </button>
                                        </td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </table>
                    <div className="row">
                        <button className="btn btn-success btn-circle-large" onClick={this.createExpense}>+</button>
                    </div>
                </div>
            </div>)
    }
}

export default ListTodosComponent