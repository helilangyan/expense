import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import 'antd/dist/antd.css';
import {  Comment, Icon, Tooltip, Avatar } from 'antd';
import { DownOutlined } from '@ant-design/icons';


class parameter extends Component {


    like = () => {
        this.setState({

        });
    };

    dislike = () => {
        this.setState({
            likes: 0,
            dislikes: 1,
            action: 'disliked',
        });
    };

    render() {


        return (
          <div> 参数设置</div>
        );
    }
}


export default parameter;