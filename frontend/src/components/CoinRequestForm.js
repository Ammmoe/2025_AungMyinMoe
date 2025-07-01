import CoinSelector from './CoinSelector';
import '../styles/CoinRequestForm.css';

export default function CoinRequestForm({ 
    targetAmount,
    setTargetAmount,
    coinDenominations,
    setCoinDenominations,
    onSubmit,
    loading,
    onReset
}) {
    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit(targetAmount, coinDenominations);
    };

    return(
        <form onSubmit={handleSubmit}>
            <div className="target-amount-container">
                <label>
                    Target Amount (&euro;):
                </label>
                <input 
                    type="number"
                    step="0.01" 
                    value={targetAmount}
                    onChange={(e) => setTargetAmount(e.target.value)}
                    placeholder="Example: 119.99" 
                />
            </div>

            <div className="coin-select-container">
                <label>Select Coins and Notes:</label>
                <CoinSelector
                    selected={coinDenominations}
                    onChange={setCoinDenominations}
                />
            </div>

            <div className="buttons-container">
                <button type="submit" disabled={loading}>
                    {loading ? 'Calculating...' : 'Calculate'}
                </button>

                <button 
                    type="button" 
                    className="reset-button"
                    onClick={onReset}
                    disabled={loading}
                >
                    Reset
                </button>
            </div>
        </form>
    );
};