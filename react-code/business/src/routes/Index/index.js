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
            theme='light'
            width={255}
            collapsed={this.state.collapsed}
          >
            <SiderNav />
          </Sider>
          <Layout>
            <Header style={{ background: '#fff', padding: '0 20px' ,color:'#333',height:'60px',borderBottom:'1px solid #C6D9EA'}}>
              <HeaderBar collapsed={this.state.collapsed} onToggle={this.toggle} />
            </Header>
            <Content>
              <ContentMain />
            </Content>
            <Footer style={{ textAlign: 'center',height:'50px',padding: '15px 0' }}>版权所有 仁爱泉智科技服务有限公司
            </Footer>
          </Layout>
        </Layout>
      </div>
    );
  }
}
export default Index