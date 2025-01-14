document.getElementById('registerForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const form = document.getElementById('registerForm');
    const formData = new FormData(form);

    fetch('/users/myPage/address/register', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            console.log(data.status);
            if (data.status === "success"){
            }
            else if (data.status === "failed"){
                alert(data.message);
            }
            else if (data.status === "forbidden"){
                alert(data.message);
            }
            window.location.href = data.url;
        });
});