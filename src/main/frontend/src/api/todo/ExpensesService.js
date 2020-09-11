import axios from 'axios'
import AuthenticationService from "../../components/todo/AuthenticationService";

class ExpensesService {
    userId = AuthenticationService.getLoggedUserId()
    getAllExpenses() {
        return axios.get(`http://localhost:8080/expenses/${this.userId}`);
    }

    deleteExpense(expenseId) {
        return axios.delete(`http://localhost:8080/expenses/${this.userId}/deleteExpense?expenseId=${expenseId}`)
    }

    getExpense(expenseId) {
        return axios.get(`http://localhost:8080/expenses/${this.userId}/getExpense?expenseId=${expenseId}`)
    }

    updateExpense(expense) {
        return axios.patch(`http://localhost:8080/expenses/${this.userId}/updateExpense`, expense)
    }

    createExpense(expense) {
        return axios.post(`http://localhost:8080/expenses/${this.userId}/createExpense`, expense)
    }
}
export default new ExpensesService()