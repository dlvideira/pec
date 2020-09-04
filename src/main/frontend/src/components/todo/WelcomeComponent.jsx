import React, {Component} from "react";
import {Link} from "react-router-dom";
import HelloWorldService from "../../api/todo/HelloWorldService";

class WelcomeComponent extends Component {
    constructor(props) {
        super(props)
        this.retrieveFromBackend = this.retrieveFromBackend.bind(this)
        this.handleSuccessfulResponse = this.handleSuccessfulResponse.bind(this)
        this.state = {
            content : ''
        }
    }
    render(){
        return (
            <>
                <h1>Welcome!</h1>
                <div className="container">
                    Welcome {this.props.match.params.name}. You can manage TODOs <Link to="/todos">here</Link>
                </div>
                <div className='container'>
                    Click here to call backend
                    <button className='btn btn-success' onClick={this.retrieveFromBackend}>Get from Backend</button>
                </div>
                <div className='cotainer'>
                    {this.state.content}
                </div>
            </>
        )
    }

    retrieveFromBackend(){
        HelloWorldService.execute()
            .then(response => this.handleSuccessfulResponse(response))
    }

    handleSuccessfulResponse(response) {
        this.setState({
            content : response.data
        })
    }
}

export default WelcomeComponent