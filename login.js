window.onload = function() {
    const urlParams = new URLSearchParams(window.location.search);
    const errorMessage = urlParams.get('error');

    if (errorMessage) {
        const errorElement = document.getElementById('errorMessage');
        errorElement.innerHTML = errorMessage;
        errorElement.style.color = 'red'; 
    }
};
