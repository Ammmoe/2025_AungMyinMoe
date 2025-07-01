import { useState } from 'react';
import CoinRequestForm from '../components/CoinRequestForm';
import CoinResultDisplay from '../components/CoinResultDisplay';
import { getMinimumCoins } from '../services/CoinService';

export default function CoinCalculatorPage() {
    const [result, setResult] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);
    const [targetAmount, setTargetAmount] = useState('');
    const [coinDenominations, setCoinDenominations] = useState([]);
    const [confirmedAmount, setConfirmedAmount] = useState(null);

    const handleCalculate = async (targetAmount, coinDenominations) => {
        setError(null);
        setResult(null);
        setLoading(true);

        if (!targetAmount) {
            setError("Target amount is required");
            setLoading(false);
            return;
        }

        try {
            const coins = await getMinimumCoins(targetAmount, coinDenominations);
            setResult(coins.coins);
            setConfirmedAmount(targetAmount);  // Save the locked-in amount for display
        } catch (err) {
            setError(err.message || "Something went wrong");
        } finally {
            setLoading(false);
        }
    };

    const handleReset = () => {
        setTargetAmount('');
        setCoinDenominations([]);
        setResult(null);
        setError(null);
        setConfirmedAmount(null);
    };

    return(
        <div>
            <h1>Minimum Change Calculator</h1>
            <CoinRequestForm 
                targetAmount={targetAmount}
                setTargetAmount={setTargetAmount}
                coinDenominations={coinDenominations}
                setCoinDenominations={setCoinDenominations}
                onSubmit={handleCalculate}
                loading={loading}
                onReset={handleReset} 
            />
            {error && <div style={{ color: 'red', marginBottom: '1rem' }}>Error: {error}</div>}
            <CoinResultDisplay 
                targetAmount={confirmedAmount}
                result={result} 
            />
        </div>
    );
};