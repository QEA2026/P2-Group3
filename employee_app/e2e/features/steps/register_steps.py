from behave import when, then
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select, WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
import uuid
import logging
import json

logger = logging.getLogger(__name__)

@when('I click "Or register"')
def step_click_or_register(context):
    context.driver.find_element(
        By.XPATH,
        "//a[text()='Or register']"
    ).click()


@then('I am directed to the register page')
def step_directed_to_register_page(context):
    WebDriverWait(context.driver, 10).until(
        EC.visibility_of_element_located(
            (By.XPATH, "//h2[text()='Create an account']")
        )
    )


@when('I enter a unique generated username and password "{password}"')
def step_enter_credentials(context, password):
    context.username = f"testuser_{uuid.uuid4().hex[:8]}"

    context.driver.find_element(
        By.ID,
        "username"
    ).send_keys(context.username)

    context.driver.find_element(
        By.ID,
        "password"
    ).send_keys(password)


@when('I select the role "Employee"')
def step_select_role_employee(context):
    role_dropdown = Select(
        context.driver.find_element(
            By.XPATH,
            "//select[@id='role']"
        )
    )

    role_dropdown.select_by_visible_text("Employee")


@when('I click the register button')
def step_click_register_button(context):
    context.driver.find_element(
        By.XPATH,
        "//button[text()='Register']"
    ).click()


@then('a register success message is displayed')
def step_success_message(context):
    try:
        # Log current state before waiting
        logger.info(f"Current URL: {context.driver.current_url}")
        logger.info(f"Page title: {context.driver.title}")

        # Capture browser console logs
        try:
            logs = context.driver.get_log('browser')
            for log in logs:
                logger.warning(f"Browser console: {log}")
        except Exception as e:
            logger.warning(f"Could not capture browser logs: {e}")

        # Wait for success message
        WebDriverWait(context.driver, 10).until(
            EC.visibility_of_element_located(
                (By.XPATH, "//div[@id='success-msg']")
            )
        )
    except TimeoutException as e:
        # Capture debug info on failure
        logger.error(f"Timeout waiting for success message")

        # Browser console logs
        try:
            logs = context.driver.get_log('browser')
            for log in logs:
                logger.error(f"Browser console: {log}")
        except Exception as ex:
            logger.error(f"Could not capture browser logs: {ex}")

        # Network logs
        try:
            network_logs = context.driver.get_log('performance')
            for log in network_logs:
                logger.error(f"Network: {log}")
        except Exception as ex:
            logger.error(f"Could not capture network logs: {ex}")

        # Page source (first 1000 chars)
        logger.error(f"Page source: {context.driver.page_source[:1000]}")

        # Screenshot
        try:
            context.driver.save_screenshot('/tmp/register_failure.png')
            logger.error("Screenshot saved to /tmp/register_failure.png")
        except Exception as ex:
            logger.error(f"Could not save screenshot: {ex}")

        raise

@then('a register error message is displayed')
def step_error_message(context):
    try:
        logger.info(f"Current URL: {context.driver.current_url}")

        WebDriverWait(context.driver, 10).until(
            EC.visibility_of_element_located(
                (By.XPATH, "//div[@id='error-msg']")
            )
        )
    except TimeoutException as e:
        logger.error(f"Timeout waiting for error message")

        try:
            logs = context.driver.get_log('browser')
            for log in logs:
                logger.error(f"Browser console: {log}")
        except Exception as ex:
            logger.error(f"Could not capture browser logs: {ex}")

        logger.error(f"Page source: {context.driver.page_source[:1000]}")
        context.driver.save_screenshot('/tmp/register_error_failure.png')
        raise