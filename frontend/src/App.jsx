
import NavTabs from "./components/NavTabs"
import TopBar from "./components/TopBar"

function App() {
 

  return (
    <>
      <TopBar/>
      <div style={{display: 'flex', marginTop: '100px',justifyContent : 'center', alignItems: 'center'}}>
      <NavTabs/>
      </div>
    </>
  )
}

export default App
