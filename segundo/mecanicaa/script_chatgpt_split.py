import sys
from PyPDF2 import PdfReader, PdfWriter

def split_pdf(input_pdf, split_page):
    reader = PdfReader(input_pdf)
    total_pages = len(reader.pages)

    if split_page < 1 or split_page >= total_pages:
        print(f"Error: split_page should be between 1 and {total_pages-1}")
        return

    writer1 = PdfWriter()
    writer2 = PdfWriter()

    # Add pages to the first PDF up to the split page (exclusive)
    for page in range(split_page):
        writer1.add_page(reader.pages[page])

    # Add the remaining pages to the second PDF starting from the split page
    for page in range(split_page, total_pages):
        writer2.add_page(reader.pages[page])

    output_pdf1 = f"part1_{input_pdf}"
    output_pdf2 = f"part2_{input_pdf}"

    with open(output_pdf1, "wb") as output1:
        writer1.write(output1)

    with open(output_pdf2, "wb") as output2:
        writer2.write(output2)

    print(f"PDF has been split into {output_pdf1} and {output_pdf2}")

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python split_pdf.py <input_pdf> <split_page>")
        sys.exit(1)

    input_pdf = sys.argv[1]
    try:
        split_page = int(sys.argv[2])
    except ValueError:
        print("Error: split_page must be an integer")
        sys.exit(1)

    split_pdf(input_pdf, split_page)
