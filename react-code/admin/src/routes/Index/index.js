import React from 'react'
import { Layout } from 'antd'
import SiderNav from '../../components/SiderNav'
import ContentMain from '../../components/ContentMain'
import HeaderBar from '../../components/HeaderBar'

const { Sider, Header, Content, Footer } = Layout


class Index extends React.Component {
  state = {
    collapsed: false
  }

  toggle = () => {
    // console.parameter(this)  状态提升后，到底是谁调用的它
    this.setState({
      collapsed: !this.state.collapsed
    })
  }
  render() {
    // 设置Sider的minHeight可以使左右自适应对齐
    return (
      <div id='page'>
        <Layout>
          <Sider collapsible
            trigger={null}
            collapsed={this.state.collapsed}
          >
            <SiderNav />
          </Sider>
          <Layout>
            <Header style={{ background: '#2da9fa', padding: '0 16px' ,color:'#ffffff',height:'50px',}}>
              <HeaderBar collapsed={this.state.collapsed} onToggle={this.toggle} />
            </Header>
            <Content>
              <ContentMain />
            </Content>
            <Footer style={{ textAlign: 'center' ,backgroundColor:'#ffffff'}}>版权所有 仁爱泉智科技服务有限公司
              {/* <a target='_blank' rel="noopener noreferrer" href='#'>github地址</a> */}
            </Footer>
          </Layout>
        </Layout>
      </div>
    );
  }
}
export default Index