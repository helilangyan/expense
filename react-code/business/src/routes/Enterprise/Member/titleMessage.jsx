import React, {Component} from 'react';
import {PlusOutlined,EditOutlined,CloseOutlined} from '@ant-design/icons';
import {Button} from 'antd'

class titleMessage extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    addDepartment=(e,type)=>{
        e.stopPropagation();
        this.props.titleClick(this.props.name.id,type);
    }

    render() {
        return (
            <div className='treeTitle'>
                <span>
                     {this.props.name.departmentName}
                </span>
                <span className="treeBtn">
                     <Button type="text" shape="circle" onClick={(e)=>this.addDepartment(e,'add')}>
                       <PlusOutlined />
                    </Button>
                </span>
                <span className="treeBtn">
                     <Button type="text" shape="circle" onClick={(e)=>this.addDepartment(e,'edit')}>
                        <EditOutlined />
                    </Button>
                </span>
                <span className="treeBtn">
                     <Button type="text" shape="circle" onClick={(e)=>this.addDepartment(e,'del')}>
                        <CloseOutlined />
                    </Button>
                </span>
            </div>
        );
    }
}

export default titleMessage;