document.getElementById('verifyForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const code = document.getElementById('code').value;

    const formData = new FormData();
    formData.append('code', code);

    fetch('/users/dormant/verify', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            console.log(data.status);
                if (data.status === "success"){
                    alert(data.message);
                    window.location.href = `/index`;
                }
                else if (data.status === "failed"){
                    alert(data.message);
                }
                else if (data.status === "forbidden"){
                    alert(data.message);
                    window.location.href = '/index';
                }
        });
});