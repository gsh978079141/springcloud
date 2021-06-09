import axios from "axios";

const API_URL_PREFIX = '/apis';

const reqOptionHandle = (url, options = {}) => {
    // default headers
    const defaultOptions = {
        headers: {
            "Content-Type": "application/json",
        },
        url,
    };

    const actualOptions = {...defaultOptions, ...options};
    if (!actualOptions.method) {
        actualOptions.method = "GET";
    }
    const methodBoold = ["POST", "PUT", "DELETE"].includes(
        actualOptions.method.toUpperCase()
    );

    if (methodBoold) {
        if (!(actualOptions.body instanceof FormData)) {
            actualOptions.headers = {
                'Content-Type': 'application/json; charset=utf-8',
                ...actualOptions.headers,
            };
            actualOptions.data = JSON.stringify(actualOptions.body);
        }
    }
    return actualOptions;
};

export default async function request(url, options) {
    const finalUrl = url.startsWith('http') ? url : `${API_URL_PREFIX}${url}`;
    const actualOptions = reqOptionHandle(finalUrl, options);
    try {
        let response = await axios(actualOptions);
        return response.data;
    } catch (err) {
        return {
            success: false,
            message: err
        }
    }

    // axios(actualOptions)
    //   .then(function (response) {
    //     console.log(JSON.stringify(response.data));
    //     return response.data;
    //   })
    //   .catch(function (error) {
    //     console.log(error);
    //   });

}
