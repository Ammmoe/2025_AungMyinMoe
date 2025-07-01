
import '../styles/CoinSelector.css';
import denominations from '../data/Denominations';

export default function CoinSelector({ selected, onChange }) {
    const toggleSelection = (value) => {
        if (selected.includes(value)) {
            onChange(selected.filter(v => v !== value));
        } else {
            onChange([...selected, value]);
        }
    };

    const coins = denominations.filter(d => d.value <= 2)
    const notes = denominations.filter(d => d.value >= 5)

    return(
        <div className="coin-selecter-container">
            <div>
                <div className="coin-grid">
                    {coins.map((denom) => (
                        <div 
                            key={denom.value}
                            className={`coin-item ${selected.includes(denom.value) ? 'selected' : ''}`}
                            onClick={() => toggleSelection(denom.value)}
                        >
                            <img src={denom.img} alt={`Denomination ${denom.value}`} />
                            {selected.includes(denom.value) && (
                                <div className="tick-overlay">✔</div>
                            )}
                        </div>
                    ))}
                </div>
            </div>

            <div>
                <div className="note-grid">
                    {notes.map((denom) => (
                        <div 
                            key={denom.value}
                            className={`note-item ${selected.includes(denom.value) ? 'selected' : ''}`}
                            onClick={() => toggleSelection(denom.value)}
                        >
                            <img src={denom.img} alt={`Denomination ${denom.value}`} />
                            {selected.includes(denom.value) && (
                                <div className="tick-overlay">&#10004;</div>
                            )}
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}