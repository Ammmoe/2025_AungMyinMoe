// Get backend url for fetching from server
const getBackendUrl = () => {
    const url = new URL(process.env.REACT_APP_BACKEND_URL || window.location.origin);
    url.port = '8080';
    return url.origin;
}

export const getMinimumCoins = async (targetAmount, coinDenominations) => {
    const response = await fetch(`${getBackendUrl()}/api/coins`, {
        method: 'POST',
        headers: { 'Content-Type' : 'application/json' },
        body: JSON.stringify({ 
            targetAmount: parseFloat(targetAmount),
            coinDenominations
        }),
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error (errorText || 'Failed to fetch');
    }

    return response.json();
}