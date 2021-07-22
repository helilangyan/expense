import React, { Component } from 'react'
import {NavLink} from 'react-router-dom'

export default class TopNav extends Component {
  render() {
    return (
      <NavLink activeClassName="selected" style={styles.topbox} {...this.props}/>
    )
  }
}
const styles = {
  topbox:{
    color:'#333',
    padding:'0 20px'
  },
  selected:{
    color:'#1890FF',
    fontWeight:'600'
  }
}