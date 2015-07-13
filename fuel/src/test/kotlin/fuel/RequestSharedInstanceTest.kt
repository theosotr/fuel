package fuel

import android.os.Environment
import fuel.core.*
import fuel.util.build
import java.io.File
import java.net.HttpURLConnection
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Created by Kittinun Vantasin on 5/22/15.
 */

class RequestSharedInstanceTest : BaseTestCase() {

    override val numberOfTestCase = 10

    enum class HttpsBin(override val path: String) : Fuel.PathStringConvertible {
        IP("ip"),
        POST("post"),
        PUT("put"),
        DELETE("delete")
    }

    class HttpBinConvertible(val method: Method, val relativePath: String) : Fuel.RequestConvertible {
        override val request = createRequest()

        fun createRequest(): Request {
            val encoder = build(Encoding()) {
                httpMethod = method
                urlString = "https://httpbin.org/$relativePath"
                parameters = mapOf("foo" to "bar")
            }
            return encoder.request
        }
    }

    override fun setUp() {
        Manager.sharedInstance.basePath = "https://httpbin.org"
        Manager.sharedInstance.additionalHeaders = mapOf("foo" to "bar")
        Manager.sharedInstance.additionalParams = mapOf("key" to "value")
    }

    public fun testHttpGetRequestWithSharedInstance() {
        var request: Request? = null
        var response: Response? = null
        var data: Any? = null
        var error: FuelError? = null

        Fuel.get("/get").responseString { req, res, either ->
            request = req
            response = res

            val (err, d) = either
            data = d
            error = err

            countdownFulfill()
        }

        countdownWait()

        assertNotNull(request, "request should not be null")
        assertNotNull(response, "response should not be null")
        assertNull(error, "error should be null")
        assertNotNull(data, "data should not be null")
        assertTrue(response?.httpStatusCode == HttpURLConnection.HTTP_OK, "http status code should be ${HttpURLConnection.HTTP_OK}")

        val string = data as String
        assertTrue(string.toLowerCase().contains("foo") && string.toLowerCase().contains("bar"), "additional header must be sent along with request and present in response")
        assertTrue(string.toLowerCase().contains("key") && string.toLowerCase().contains("value"), "additional param must be sent along with request and present in response")
        assertTrue(string.contains("https"), "url should contain https to indicate usage of shared instance")
    }

    public fun testHttpPostRequestWithSharedInstance() {
        var request: Request? = null
        var response: Response? = null
        var data: Any? = null
        var error: FuelError? = null

        Fuel.post("/post").responseString { req, res, either ->
            request = req
            response = res

            val (err, d) = either
            data = d
            error = err

            countdownFulfill()
        }

        countdownWait()

        val string = data as String

        assertNotNull(request, "request should not be null")
        assertNotNull(response, "response should not be null")
        assertNull(error, "error should be null")
        assertNotNull(data, "data should not be null")
        assertTrue(response?.httpStatusCode == HttpURLConnection.HTTP_OK, "http status code should be ${HttpURLConnection.HTTP_OK}")

        assertTrue(string.toLowerCase().contains("foo") && string.toLowerCase().contains("bar"), "additional header must be sent along with request and present in response")
        assertTrue(string.toLowerCase().contains("key") && string.toLowerCase().contains("value"), "additional param must be sent along with request and present in response")
        assertTrue(string.contains("https"), "url should contain https to indicate usage of shared instance")
    }

    public fun testHttpPutRequestWithSharedInstance() {
        var request: Request? = null
        var response: Response? = null
        var data: Any? = null
        var error: FuelError? = null

        Fuel.put("/put").responseString { req, res, either ->
            request = req
            response = res

            val (err, d) = either
            data = d
            error = err

            countdownFulfill()
        }

        countdownWait()

        val string = data as String

        assertNotNull(request, "request should not be null")
        assertNotNull(response, "response should not be null")
        assertNull(error, "error should be null")
        assertNotNull(data, "data should not be null")
        assertTrue(response?.httpStatusCode == HttpURLConnection.HTTP_OK, "http status code should be ${HttpURLConnection.HTTP_OK}")

        assertTrue(string.toLowerCase().contains("foo") && string.toLowerCase().contains("bar"), "additional header must be sent along with request and present in response")
        assertTrue(string.toLowerCase().contains("key") && string.toLowerCase().contains("value"), "additional param must be sent along with request and present in response")
        assertTrue(string.contains("https"), "url should contain https to indicate usage of shared instance")
    }

    public fun testHttpDeleteRequestWithSharedInstance() {
        var request: Request? = null
        var response: Response? = null
        var data: Any? = null
        var error: FuelError? = null

        Fuel.delete("/delete").responseString { req, res, either ->
            request = req
            response = res

            val (err, d) = either
            data = d
            error = err

            countdownFulfill()
        }

        countdownWait()

        val string = data as String

        assertNotNull(request, "request should not be null")
        assertNotNull(response, "response should not be null")
        assertNull(error, "error should be null")
        assertNotNull(data, "data should not be null")
        assertTrue(response?.httpStatusCode == HttpURLConnection.HTTP_OK, "http status code should be ${HttpURLConnection.HTTP_OK}")

        assertTrue(string.toLowerCase().contains("foo") && string.toLowerCase().contains("bar"), "additional header must be sent along with request and present in response")
        assertTrue(string.toLowerCase().contains("key") && string.toLowerCase().contains("value"), "additional param must be sent along with request and present in response")
        assertTrue(string.contains("https"), "url should contain https to indicate usage of shared instance")
    }

    public fun testHttpGetRequestWithPathStringConvertibleAndSharedInstance() {
        var request: Request? = null
        var response: Response? = null
        var data: Any? = null
        var error: FuelError? = null

        Fuel.get(HttpsBin.IP).responseString { req, res, either ->
            request = req
            response = res

            val (err, d) = either
            data = d
            error = err

            countdownFulfill()
        }

        countdownWait()

        val string = data as String

        assertNotNull(request, "request should not be null")
        assertNotNull(response, "response should not be null")
        assertNull(error, "error should be null")
        assertNotNull(data, "data should not be null")
        assertTrue(response?.httpStatusCode == HttpURLConnection.HTTP_OK, "http status code should be ${HttpURLConnection.HTTP_OK}")

        assertTrue(string.contains("origin"), "response should contain \"origin\" ")
    }

    public fun testHttpPostRequestWithPathStringConvertibleAndSharedInstance() {
        var request: Request? = null
        var response: Response? = null
        var data: Any? = null
        var error: FuelError? = null

        Fuel.post(HttpsBin.POST).responseString { req, res, either ->
            request = req
            response = res

            val (err, d) = either
            data = d
            error = err

            countdownFulfill()
        }

        countdownWait()

        val string = data as String

        assertNotNull(request, "request should not be null")
        assertNotNull(response, "response should not be null")
        assertNull(error, "error should be null")
        assertNotNull(data, "data should not be null")
        assertTrue(response?.httpStatusCode == HttpURLConnection.HTTP_OK, "http status code should be ${HttpURLConnection.HTTP_OK}")

        assertTrue(string.contains("https"), "url should contain https to indicate usage of shared instance")
    }

    public fun testHttpPutRequestWithPathStringConvertibleAndSharedInstance() {
        var request: Request? = null
        var response: Response? = null
        var data: Any? = null
        var error: FuelError? = null

        Fuel.put(HttpsBin.PUT).responseString { req, res, either ->
            request = req
            response = res

            val (err, d) = either
            data = d
            error = err

            countdownFulfill()
        }

        countdownWait()

        val string = data as String

        assertNotNull(request, "request should not be null")
        assertNotNull(response, "response should not be null")
        assertNull(error, "error should be null")
        assertNotNull(data, "data should not be null")
        assertTrue(response?.httpStatusCode == HttpURLConnection.HTTP_OK, "http status code should be ${HttpURLConnection.HTTP_OK}")

        assertTrue(string.contains("https"), "url should contain https to indicate usage of shared instance")
    }

    public fun testHttpDeleteRequestWithPathStringConvertibleAndSharedInstance() {
        var request: Request? = null
        var response: Response? = null
        var data: Any? = null
        var error: FuelError? = null

        Fuel.delete(HttpsBin.DELETE).responseString { req, res, either ->
            request = req
            response = res

            val (err, d) = either
            data = d
            error = err

            countdownFulfill()
        }

        countdownWait()

        val string = data as String

        assertNotNull(request, "request should not be null")
        assertNotNull(response, "response should not be null")
        assertNull(error, "error should be null")
        assertNotNull(data, "data should not be null")
        assertTrue(response?.httpStatusCode == HttpURLConnection.HTTP_OK, "http status code should be ${HttpURLConnection.HTTP_OK}")

        assertTrue(string.contains("https"), "url should contain https to indicate usage of shared instance")
    }

    public fun testHttpPostRequestWithRequestConvertibleAndSharedInstance() {
        var request: Request? = null
        var response: Response? = null
        var data: Any? = null
        var error: FuelError? = null

        Fuel.request(HttpBinConvertible(Method.POST, "post")).responseString { req, res, either ->
            request = req
            response = res

            val (err, d) = either
            data = d
            error = err

            countdownFulfill()
        }

        countdownWait()

        assertNotNull(request, "request should not be null")
        assertNotNull(response, "response should not be null")
        assertNull(error, "error should be null")
        assertNotNull(data, "data should not be null")
        assertTrue(response?.httpStatusCode == HttpURLConnection.HTTP_OK, "http status code should be ${HttpURLConnection.HTTP_OK}")
    }

    public fun testHttpDownloadWithProgressValidCase() {
        var request: Request? = null
        var response: Response? = null
        var data: Any? = null
        var error: FuelError? = null

        var read = -1L
        var total = -1L

        Fuel.download("/bytes/1048576").destination { response, url ->
            val sd = Environment.getExternalStorageDirectory();
            val location = File(sd.getAbsolutePath() + "/test")
            location.mkdir()
            File(location, "downloadFromFuelWithProgress.tmp")
        }.progress { readBytes, totalBytes ->
            read = readBytes
            total = totalBytes
        }.responseString { req, res, either ->
            request = req
            response = res
            val (err, d) = either
            data = d
            error = err

            countdownFulfill()
        }

        countdownWait()

        assertNotNull(request, "request should not be null")
        assertNotNull(response, "response should not be null")
        assertNull(error, "error should be null")
        assertNotNull(data, "data should not be null")

        assertTrue(read == total, "read bytes and total bytes should be equal")
        val statusCode = HttpURLConnection.HTTP_OK
        assertTrue(response?.httpStatusCode == statusCode, "http status code of invalid credential should be $statusCode")
    }

}