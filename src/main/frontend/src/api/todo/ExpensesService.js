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
}
export default new ExpensesService()