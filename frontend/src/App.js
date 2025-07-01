import './App.css';
import { Routes, Route } from 'react-router-dom';
import CoinCalculatorPage from './pages/CoinCalculatorPage';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<CoinCalculatorPage />} />
      </Routes>
    </div>
  );
}

export default App;
