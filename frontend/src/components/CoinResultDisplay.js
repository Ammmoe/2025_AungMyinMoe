import '../styles/CoinResultDisplay.css'
import denominations from '../data/Denominations';

export default function CoinResultDisplay({ targetAmount, result }) {
    if (!result || !targetAmount) return null;

    // Count occurrences of each denomination
    const counts = result.reduce((acc, coin) => {
        acc[coin] = (acc[coin] || 0) + 1;
        return acc;
    }, {});

    return (
        <div className="result-container">
            <label>Minimum Change for €{parseFloat(targetAmount)}:</label>
            <div className="result-section">
                <ul className="result-display-list">
                    {Object.entries(counts)
                        .sort(([a], [b]) => parseFloat(a) - parseFloat(b))
                        .filter(([value]) => parseFloat(value) <= 2) // Coins only
                        .map(([value, count]) => {
                            const denom = denominations.find(d => d.value === parseFloat(value));
                            return (
                                <li key={value} className="result-item">
                                    <span className="bold-text">{count} x</span>
                                    {denom ? (
                                        <img src={denom.img} alt={`Denomination ${value}`} className="coin-image" />
                                    ) : (
                                        <span>{value}</span>
                                    )}
                                </li>
                            );
                        })}
                </ul>
            </div>

            <div className="result-section">
                <ul className="result-display-list">
                    {Object.entries(counts)
                        .sort(([a], [b]) => parseFloat(a) - parseFloat(b))
                        .filter(([value]) => parseFloat(value) >= 5) // Notes only
                        .map(([value, count]) => {
                            const denom = denominations.find(d => d.value === parseFloat(value));
                            return (
                                <li key={value} className="result-item">
                                    <span className="bold-text">{count} x</span>
                                    {denom ? (
                                        <img src={denom.img} alt={`Denomination ${value}`} className="note-image" />
                                    ) : (
                                        <span>{value}</span>
                                    )}
                                </li>
                            );
                        })}
                </ul>
            </div>
        </div>
    );
}
