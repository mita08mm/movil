import { Injectable } from '@nestjs/common';
import { promises as fs } from 'fs';
import { GoogleGenAI } from '@google/genai';
import { NotesService } from '../notes/services/notes.service';

const aiPrompt = `Act as an advanced OCR engine.
Analyze the image and accurately transcribe its content according to the following format and rules:
Output Rules:

Produce a single, well-structured output block that may include:
  * Plain Text

  * Mermaid Diagram (if diagrams are present)

  * KaTeX-formatted Mathematical Formulas (if equations are present)

Diagram Handling:

* If the image contains any diagrams (e.g., flowcharts, ER diagrams), render them using Mermaid syntax (version 11.5.0).

* Ensure Mermaid code:
  * Follows common diagram conventions
  * Is free from syntax errors
  * Is easily editable and human-readable

Mathematical Content:
  * If the image contains mathematical equations or formulas, render them in KaTeX.
  * Use \\newline for line breaks in KaTeX.
  * Ensure all equations are mathematically correct and clearly formatted.

Markdown Output Format:
* Use fenced code blocks:
  * For KaTeX:
    \`\`\`latex  
    a^2 + b^2 = c^2  
    \`\`\`  
  * For Mermaid diagrams:
    \`\`\`mermaid  
    graph TD  
    A --> B  
    \`\`\`  
* All other content (non-diagram, non-math) should be returned as plain text.

* If the image does not contain plain text, diagrams, or mathematical content, return an empty string.

Important: Do not use dollar signs ($) for KaTeX formatting. Ensure output order: plain text → Mermaid → KaTeX, if multiple formats are present.`;

export interface MulterFile {
  destination: string;
  mimetype: string;
  filename: string;
  path: string;
  originalname: string;
  size: number;
}

@Injectable()
export class OcrService {
  constructor(private noteService: NotesService) {}

  async processImage(
    file: MulterFile,
    userId: string,
    categoryId: string,
  ) {
    try {
      const imageBuffer = await fs.readFile(file.path, { encoding: 'base64' });

      const resultText = await this.aiOcrProcessImage(imageBuffer, file.mimetype);

      await this.cleanupFile(file.path);

      return this.noteService.create({
        title: 'OCR Result',
        content: resultText,
        categoryId: categoryId,
      }, userId);
    } catch (error) {
      await this.cleanupFile(file.path);

      throw error;
    }
  }

  private async aiOcrProcessImage(base64Image: string, mimeType: string): Promise<string> {
    const ai = new GoogleGenAI({
      apiKey: process.env.GEMINI_API_KEY,
    });

    const config = {
      responseMimeType: 'text/plain',
      systemInstruction: [
        {
          text: aiPrompt,
        }
      ],
    };
    const model = 'gemini-2.0-flash';

    const contents = [
      {
        role: 'user',
        parts: [
          {
            inlineData: {
              data: base64Image,
              mimeType: mimeType,
            },
          },
        ],
      },
    ];

    const response = await ai.models.generateContentStream({
      model,
      config,
      contents,
    });

    const chunks = [];

    for await (const chunk of response) {
      chunks.push(chunk.text);
    }

    return chunks.join('\n');
  }

  private async cleanupFile(filePath: string) {
    try {
      await fs.unlink(filePath);
    } catch (error) {
      console.error('Error cleaning up file:', error);
    }
  }
} 