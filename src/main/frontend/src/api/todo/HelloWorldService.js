import axios from 'axios'

class HelloWorldService {
    execute() {
        return axios.get('http://localhost:8080/expenses/5f3ec5079bb5ae3114c544e5');
    }
}
export default new HelloWorldService()