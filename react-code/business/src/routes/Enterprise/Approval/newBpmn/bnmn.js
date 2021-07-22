import React, { Component } from 'react';
import { Button } from 'antd';
import { PlusCircleOutlined,ArrowRightOutlined, ArrowLeftOutlined,ZoomInOutlined, ZoomOutOutlined,UndoOutlined } from '@ant-design/icons';
import BpmnModeler from 'bpmn-js/lib/Modeler'
// import {diagramXML} from './xml'
import './Bpmn.css'
import 'bpmn-js/dist/assets/diagram-js.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css'
import {getBusinessObject} from "bpmn-js/lib/util/ModelUtil";
import camundaExtension from './resources/camunda';
import Panel from './panel'
import {message} from "antd/lib/index";


var viewer=null;
var element=null;
var businessObject=null;
class bpmn extends Component {
    async componentDidMount(){
        const diagramXML=`<?xml version="1.0" encoding="UTF-8"?>
                            <bpmn2:definitions xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" id="sample-diagram" targetNamespace="http://bpmn.io/schema/bpmn" xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd">
                              <bpmn2:process id="key_`+this.state.enterpriseId+`_`+this.props.xmlType+`" name="报销填报">
                                <bpmn2:startEvent id="StartEvent_1" name="开始" />
                              </bpmn2:process>
                              <bpmndi:BPMNDiagram id="BPMNDiagram_1">
                                <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="key_123">
                                  <bpmndi:BPMNShape id="_BPMNShape_StartEvent_2" bpmnElement="StartEvent_1">
                                    <dc:Bounds x="184" y="92" width="36" height="36" />
                                    <bpmndi:BPMNLabel>
                                      <dc:Bounds x="191" y="68" width="22" height="14" />
                                    </bpmndi:BPMNLabel>
                                  </bpmndi:BPMNShape>
                                </bpmndi:BPMNPlane>
                              </bpmndi:BPMNDiagram>
                            </bpmn2:definitions>
                            `;
        const that=this;
        let xmlText='';
        await this.get('/bpm/design/design/getDefinitionXMLDeployId/'+this.state.enterpriseId+'/'+this.props.xmlType).then(res=>{
            if (res.code==1){
                xmlText=res.data;
                this.initBpmn(xmlText);
            } else {
                xmlText=diagramXML;
                this.initBpmn(xmlText);
            }
            that.props.textChange(xmlText)
        })


    }
    initBpmn(xmlText){
        const that=this;
        viewer = new BpmnModeler({
            container: '#canvas',
            moddleExtensions: {camunda: camundaExtension},
        });
        viewer.importXML(xmlText, function(err) {
            if (err) {
                console.error('failed to load diagram');
                console.error(err);
                return console.log('failed to load diagram', err);
            }
        });
        that.setState({
            viewer:viewer
        })
        viewer.on('element.click',(event)=>{
            let eventElement=getBusinessObject(event.element)
            if (eventElement.$type=='bpmn:UserTask') {
                this.getApplyData(eventElement.id)
            }
            that.setState({
                show:true,
                element:event.element,
                businessObject:getBusinessObject(event.element),
            })
        })
        function saveDiagram(done) {
            // 把传入的done再传给bpmn原型的saveXML函数调用
            viewer.saveXML({ format: true }, function (err, xml) {
                done(err, xml)
            })
        }
        viewer.on('commandStack.changed', function (e) {
            saveDiagram(function (err, xml) {
                that.setState({
                    xmlStr:xml
                })
                that.props.textChange(xml)
            })
        })
    }
    getApplyData=(id)=>{
        this.get('/bpm/design/design/usertasks/'+id).then(res=>{
            if (res.code==1){
                let userData=[];
                res.data.forEach(item=>{
                    userData.push({
                        userid:item.operatorId,
                        username:item.operatorName,
                    })
                })
                this.setState({
                    applyData:userData
                })
            }
        })
    }
    setApply=(data)=>{
        this.setState({
            applyData:data
        })
    }
    clearApply=(index)=>{
        let applyData=this.state.applyData;
        applyData.splice(index,1)
        this.setState({
            applyData:applyData
        })
    }
    saveUser=()=>{
        let userData=this.state.applyData;
        let obj = {
            stepid:this.state.businessObject.id,
            auditUserDtos:JSON.stringify(userData)
        };
        this.post('/bpm/design/design/stepuser/add',obj).then(res=>{
            message.success('保存成功')
        })
    }


    render() {
        return (
            <div>
                <div>
                    <div id="canvas" style={{height: '100vh',marginTop:'50px'}} onClick={this.bpmnClick}/>
                    {/*<div className="properties-panel"></div>*/}
                    <div className="allButton">
                        {/*<Button className="fontBtn" size={'large'} icon={<PlusCircleOutlined />}></Button>*/}
                        <Button className="fontBtn" icon={<ArrowRightOutlined />} onClick={()=>{this.handleRedo()}}></Button>
                        <Button className="fontBtn" icon={<ArrowLeftOutlined />} onClick={()=>{this.handleUndo()}}></Button>
                        <Button className="fontBtn" icon={<ZoomInOutlined />} onClick={()=>{this.handleZoom(1.2)}}></Button>
                        <Button className="fontBtn" icon={<ZoomOutOutlined />} onClick={()=>{this.handleZoom(0.8)}}></Button>
                        <Button className="fontBtn" icon={<UndoOutlined />} onClick={()=>{this.resetView()}}></Button>
                    </div>
                    {
                        this.state.show? <Panel element={this.state.element}
                                                businessObject={this.state.businessObject}
                                                applyData={this.state.applyData}
                                                setApply={this.setApply}
                                                saveUser={this.saveUser}
                                                clearApply={this.clearApply}
                                                moddle={viewer} updateAttr={this.updateAttr}></Panel>:''
                    }

                    {/*  <panel ref="panel" :element="element" :businessObject="businessObject" :moddle="moddle" :modeling="modeling"/>*/}
                </div>
            </div>
         );
    }
    constructor(props) {
        super(props);
        this.state = {
            newScale:1,
            show:false,
            element:null,
            businessObject:null,
            applyData:[],
            formType: ["bpmn:StartEvent", "bpmn:UserTask"],
            enterpriseId:JSON.parse(localStorage.getItem("entInfo")).enterpriseId
        }
    }
    //修改表单
    updateAttr=(type, v)=> {
        let businessObject=this.state.businessObject;
        businessObject[type] = v.target.value
        console.log(businessObject);
        if (type=='documentation') {
            businessObject[type] = v
        }
        if (type=='name') {
            const modeling = viewer.get('modeling');
            modeling.updateLabel(this.state.element, v.target.value);
        }
        this.setState({
            businessObject:businessObject
        })
        console.log(type);
        console.log(v);
    }
    updateProperties() {
        let obj = {}
        let businessObject=this.state.businessObject;
        const modeling = viewer.get('modeling');
            if (this.state.formType.includes(businessObject.$type)) {
            obj.extensionElements = this.extensionElements
        }
        if (['bpmn:TimerEventDefinition'].includes(businessObject.$type)) {
            obj.eventDefinitions = businessObject.eventDefinitions
        }
        if (['bpmn:SequenceFlow'].includes(businessObject.$type)) {
            obj.conditionExpression = businessObject.conditionExpression
        } else {
            obj.documentation = businessObject.documentation
        }
        modeling.updateProperties(this.state.element, obj);
        console.log(this.businessObject)
    }
    // 前进
    handleRedo() {
        viewer.get("commandStack").redo();
    }
    // 后退
    handleUndo() {
       viewer.get("commandStack").undo();
    }
    // 放大缩小
    handleZoom=(radio)=>{
        let newScale=this.state.newScale;
        newScale *= radio;
        this.setState({
            newScale:newScale
        })
        viewer.get("canvas").zoom(newScale);
    }
    //恢复到原位
    resetView() {
        viewer.get("canvas").zoom("fit-viewport");
    }

}

export default bpmn;
